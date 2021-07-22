package com.inthebytes.accountservice.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.accountservice.dao.PasswordChangeDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.PasswordChange;
import com.inthebytes.accountservice.entity.User;
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

	@Value("${SL_DOMAIN_PROTOCOL}")
	private String domainProtocol;

	@Value("${SL_DOMAIN_HOST}")
	private String domainName;
	
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
		PasswordChange prevToken = passRepo.findByUser(user);
		if (prevToken != null) {
			passRepo.delete(prevToken);
			passRepo.flush();
		}
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
	
	public Boolean changePassword(String token, String newPassword) {
		PasswordChange savedToken = passRepo.findByConfirmationToken(token);
		if (savedToken == null) {
			throw new TokenDoesNotExistException("The token "+token+" could not be found");
		} else if (savedToken.getCreatedTime().before(Date.from(Instant.now().minusSeconds((long) (60 * 30))))) {
			passRepo.delete(savedToken);
			return false;
		}
		User user = savedToken.getUser();
		String password = new BCryptPasswordEncoder().encode(newPassword);
		user.setPassword(password);
		user = userRepo.save(user);
		passRepo.delete(savedToken);
		if (user == null) {
			throw new UserDoesNotExistException("Token not associated with a valid user");
		} else if (!user.getPassword().equals(password)) {
			throw new RuntimeException("Failed to change password");
		} else return true;
	}
	
	private void sendChangeToken(String token, String email){
		String link = domainProtocol +"://"+ domainName +"/reset-password/"+ token;
		
		// The email body for non-HTML email clients
		String bodyText = "A password reset has been requested for your StackLunch account."
				+ "\\nIf this was done by you, please click the following link "
				+ "to reset your password. If not, you can ignore this email.";

		// The HTML body of the email
		String bodyHTML = "A password reset has been requested for your StackLunch account."
				+ "<br>If this was done by you, please click the following link "
				+ "to reset your password. If not, you can ignore this email.";
		
		emailService.send(mailUserName, email, "Password Reset", bodyText, bodyHTML, link, "Change Password");
	}
}
