package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.ConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.Confirmation;
import com.inthebytes.accountservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> confirmUserAccount(String confirmationToken) {
		Confirmation token = confirmationDao.findByConfirmationToken(confirmationToken);

		if (token != null) {
			if (token.getCreatedDate().toLocalDateTime().plusSeconds(confirmationWindowSeconds).isAfter(LocalDateTime.now())) {
				User user = token.getUser();
				user.setActive(true);
				token.setConfirmed(true);
				confirmationDao.save(token);
				userDao.save(user);
				return new ResponseEntity<>("Account confirmed!", HttpStatus.OK);
			} else {
				// TODO: redirect to obtain a new token
				return new ResponseEntity<>("Token expired.", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Invalid verification token.", HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<String> verifyUser(String email) {
		User existingUser = userDao.findByEmailIgnoreCase(email);

		if (existingUser == null) {
			return new ResponseEntity<>("Email doesn't exist", HttpStatus.UNAUTHORIZED);
		}

		Confirmation existingUserConfirmation = confirmationDao.findConfirmationByUser(existingUser);

		if (existingUserConfirmation != null && existingUserConfirmation.getConfirmed()) {
			return new ResponseEntity<>("User already confirmed!", HttpStatus.OK);
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

		return new ResponseEntity<>("Account created. Please check your email to verify your account.", HttpStatus.CREATED);
	}
}
