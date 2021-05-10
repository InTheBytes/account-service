package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.ConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.Confirmation;
import com.inthebytes.accountservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.model.SesException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationService {

	@Value("${SL_EMAIL}")
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

	public String verifyUser(String email) throws MessagingException, SesException, IOException {
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

		// The email body for non-HTML email clients
		String bodyText = "To confirm your account, please follow the link: \r\n"
				+"http://localhost:8080/user/confirm-account?token="+ confirmation.getConfirmationToken();

		// The HTML body of the email
		String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>To confirm your account, please follow the link</h1>"
				+ "<a href=\"http://localhost:8080/user/confirm-account?token="+ confirmation.getConfirmationToken() +"\">Confirmation Link</a>" + "</body>" + "</html>";

		emailSendService.send(mailUserName, existingUser.getEmail(), "Complete Registration", bodyText, bodyHTML);

		return "Account created. Please check your email to verify your account.";
	}
}
