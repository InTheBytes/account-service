package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.UserConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.entity.UserConfirmation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service("confirmationService")
public class ConfirmationService {

	@Value("${spring.mail.username}")
	private String mailUserName;

	private final UserDao userDao;

	private final UserConfirmationDao userConfirmationDao;

	private final EmailSendService emailSendService;

	private final Integer confirmationWindowSeconds = 60 * 15; // 60 sec * 15 min

	public ConfirmationService(UserDao userDao, UserConfirmationDao userConfirmationDao, EmailSendService emailSendService) {
		this.userDao = userDao;
		this.userConfirmationDao = userConfirmationDao;
		this.emailSendService = emailSendService;
	}

	@Transactional
	public ResponseEntity<String> confirmUserAccount(String confirmationToken) {
		UserConfirmation token = userConfirmationDao.findByConfirmationToken(confirmationToken);

		if (token != null) {
			if (token.getCreatedDate().toLocalDateTime().plusSeconds(confirmationWindowSeconds).isAfter(LocalDateTime.now())) {
				User user = userDao.findByUserId(token.getUserId());
				user.setActive(true);
				token.setConfirmed(true);
				userConfirmationDao.save(token);
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

		UserConfirmation existingUserConfirmation = userConfirmationDao.findUserConfirmationByUserId(existingUser.getUserId());

		if (existingUserConfirmation != null && existingUserConfirmation.getConfirmed()) {
			return new ResponseEntity<>("User already confirmed!", HttpStatus.OK);
		}

		// Create confirmation
		UserConfirmation userConfirmation = new UserConfirmation();
		userConfirmation.setUserId(existingUser.getUserId());
		userConfirmation.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		userConfirmation.setConfirmationToken(UUID.randomUUID().toString());
		userConfirmation.setUserByUserId(existingUser);
		userConfirmation.setConfirmed(false);
		userConfirmationDao.save(userConfirmation);

		// Create email message
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(existingUser.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom(mailUserName);
		mailMessage.setText("To confirm your account, please click here : "
				+"http://localhost:8080/user/confirm-account?token="+ userConfirmation.getConfirmationToken());

		emailSendService.sendMail(mailMessage);

		return new ResponseEntity<>("Account created. Please check your email to verify your account.", HttpStatus.CREATED);
	}
}
