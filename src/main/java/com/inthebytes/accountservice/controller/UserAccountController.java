package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.dao.UserConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.entity.UserConfirmation;
import com.inthebytes.accountservice.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserAccountController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserConfirmationDao userConfirmationDao;

	@Autowired
	private EmailSendService emailSendService;

	@PutMapping(path="/confirm-account", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> confirmUserAccount(@RequestParam("token")String confirmationToken) {
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

	@PostMapping(value="/verify", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> verifyUser(@RequestParam("email") String email) {
		User existingUser = userDao.findByEmailIgnoreCase(email);

		if (existingUser == null) {
			return new ResponseEntity<>("Email doesn't exist", HttpStatus.NOT_FOUND);
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
