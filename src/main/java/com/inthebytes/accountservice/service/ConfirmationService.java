package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.ConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.Confirmation;
import com.inthebytes.accountservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationService {

	@Value("${spring.mail.username}")
	private String mailUserName;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ConfirmationDao confirmationDao;

	@Autowired
	private EmailSendService emailSendService;

	private final Integer confirmationWindowSeconds = 60 * 15; // 60 sec * 15 min

	@Transactional
	public String confirmUserAccount(String confirmationToken) {
		Confirmation token = confirmationDao.findByConfirmationToken(confirmationToken);

		if (token != null) {
			if (token.getCreatedDate().toLocalDateTime().plusSeconds(confirmationWindowSeconds).isAfter(LocalDateTime.now())) {
				User user = token.getUser();
				user.setActive(true);
				token.setConfirmed(true);
				confirmationDao.save(token);
				userDao.save(user);
				return "Account confirmed!";
			} else {
				// TODO: redirect to obtain a new token
				return "Token expired.";
			}
		} else {
			return "Invalid verification token.";
		}
	}

	public String verifyUser(String email) {
		User existingUser = userDao.findByEmailIgnoreCase(email);

		if (existingUser == null) {
			return "Email doesn't exist";
		}

		Confirmation existingUserConfirmation = confirmationDao.findConfirmationByUser(existingUser);

		if (existingUserConfirmation != null && existingUserConfirmation.getConfirmed()) {
			return "User already confirmed!";
		}

		// Create confirmation
		Confirmation confirmation = new Confirmation();
		confirmation.setUser(existingUser);
		confirmation.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		confirmation.setConfirmationToken(UUID.randomUUID().toString());
		confirmation.setUser(existingUser);
		confirmation.setConfirmed(false);
		confirmationDao.save(confirmation);

		// Create email message
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(existingUser.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom(mailUserName);
		mailMessage.setText("To confirm your account, please click here : "
				+"http://localhost:8080/user/confirm-account?token="+ confirmation.getConfirmationToken());

		emailSendService.sendMail(mailMessage);

		return "Account created. Please check your email to verify your account.";
	}
}
