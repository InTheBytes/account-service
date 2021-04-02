package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.UserConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.entity.UserConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service("confirmationService")
public class ConfirmationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserConfirmationDao userConfirmationDao;

	@Autowired
	private EmailSendService emailSendService;

	public ResponseEntity<String> confirmUserAccount(String confirmationToken) {
		UserConfirmation token = userConfirmationDao.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userDao.findByUserId(token.getUserId());
			token.setConfirmed(true);
			userConfirmationDao.save(token);
			return new ResponseEntity<>("Account confirmed!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid verification token.", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> verifyUser(String email) {
		User existingUser = userDao.findByEmailIgnoreCase(email);

		if (existingUser == null) {
			return new ResponseEntity<>("Email doesn't exist", HttpStatus.NOT_FOUND);
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

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(existingUser.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("inthebytesss@gmail.com");
		mailMessage.setText("To confirm your account, please click here : "
				+"http://localhost:8080/user/confirm-account?token="+ userConfirmation.getConfirmationToken());

		emailSendService.sendMail(mailMessage);

		return new ResponseEntity<>("Account created. Please check your email to verify your account.", HttpStatus.CREATED);
	}
}
