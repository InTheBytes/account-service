package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.dao.UserConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.entity.UserConfirmation;
import com.inthebytes.accountservice.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.List;
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

	@GetMapping(path="/confirm-account", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public String confirmUserAccount(@RequestParam("token")String confirmationToken) {
		UserConfirmation token = userConfirmationDao.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userDao.findByUserId(token.getUserId());
			token.setConfirmed(true);
			userConfirmationDao.save(token);
			return "Account confirmed!";
		} else {
			return "Invalid verification token.";
		}
	}

	@GetMapping(value="/verify")
	public String verifyUser(@RequestParam("email") String email) {
		User existingUser = userDao.findByEmailIgnoreCase(email);

		if (existingUser == null) {
			return "Email doesn't exists.";
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

		return "Account created. Please check your email to verify your account.";
	}
}
