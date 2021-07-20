package com.inthebytes.accountservice.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.accountservice.dao.PasswordChangeDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.PasswordChange;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.exception.MessagingFailedException;
import com.inthebytes.accountservice.exception.TokenDoesNotExistException;
import com.inthebytes.accountservice.exception.UserDoesNotExistException;


@Service
@Transactional
public class PasswordChangeService {
	
	@Autowired
	private EmailSendService emailService;
	
	@Autowired
	private UserDao userRepo;
	
	@Autowired
	private PasswordChangeDao passRepo;
	
	@Value("${SL_EMAIL}")
	private String mailUserName;
	
	@Autowired
	private ServerProperties server;
	
	public Boolean changePassword(String token, String newPassword) {
		PasswordChange savedToken = passRepo.findByConfirmationToken(token);
		if (token == null) {
			throw new TokenDoesNotExistException("The token "+token+" could not be found");
		} else if (savedToken.getCreatedTime().before(Date.from(Instant.now().minusSeconds((long) (60 * 30))))) {
			passRepo.delete(savedToken);
			return false;
		}
		User user = savedToken.getUser();
		String password = new BCryptPasswordEncoder().encode(newPassword);
		user.setPassword(password);
		user = userRepo.save(user);
		if (user == null) {
			throw new UserDoesNotExistException("Token not associated with a valid user");
		} else if (!user.getPassword().equals(password)) {
			throw new RuntimeException("Failed to change password");
		} else return true;
	}
	
	public void sendChangeTokenByEmail(String email) {
		User user = userRepo.findByEmailIgnoreCase(email);
		if (user == null) {
			throw new UserDoesNotExistException("User not found with email address " + email);
		}
		sendChangeToken(generateToken(user).getConfirmationToken(), email);
	}
	
	public void sendChangeTokenByUsername(String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UserDoesNotExistException("User not found with username " + username);
		}
		sendChangeToken(generateToken(user).getConfirmationToken(), user.getEmail());
	}
	
	private PasswordChange generateToken(User user) {
		PasswordChange token = new PasswordChange();
		token.setConfirmationToken(UUID.randomUUID().toString());
		token.setUser(user);
		token.setCreatedTime(Timestamp.from(Instant.now()));
		token = passRepo.save(token);
		if (token == null) {
			throw new RuntimeException("Failed to create token");
		} else {
			return token;
		}
	}
	
	private void sendChangeToken(String token, String email){
		String address = (server.getPort() == 8082) ? "localhost:3000/" : "https://stacklunch.com/";
		
		// The email body for non-HTML email clients
		String bodyText = "To reset your password, please follow the link: \r\n"
				+address+"user/confirm-account?token=";

		// The HTML body of the email
		String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>To reset your password, please follow the link</h1>"
				+ "<a href=\""+address+"user/confirm-account?token="+ token+"\">Confirmation Link</a>" + "</body>" + "</html>";
		
		try {
			emailService.send(mailUserName, email, "Reset Password", bodyText, bodyHTML);
		} catch (Exception e) {
			throw new MessagingFailedException("Failed to send password reset link");
		}
	}

}
