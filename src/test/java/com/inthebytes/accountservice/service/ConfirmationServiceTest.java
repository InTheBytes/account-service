package com.inthebytes.accountservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthebytes.accountservice.dao.ConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.stacklunch.data.confirmation.Confirmation;
import com.inthebytes.stacklunch.data.user.User;

import software.amazon.awssdk.services.ses.model.SesException;

@ExtendWith(MockitoExtension.class)
public class ConfirmationServiceTest {
	
	@Mock
	UserDao userDao;

	@Mock
	ConfirmationDao confirmationDao;

	@Mock
	EmailSendService emailSendService;

	@Autowired
	@InjectMocks
	ConfirmationService confirmationService;

	@Test
	public void test_confirmUserAccount_confirm_positive() {
		
		when(confirmationDao.findByConfirmationToken("validtoken")).thenReturn(validToken(false));
		assertEquals("Account confirmed!", confirmationService.confirmUserAccount("validtoken"));
	}

	@Test
	public void test_confirmUserAccount_expired_negative() {

		when(confirmationDao.findByConfirmationToken("123")).thenReturn(expiredToken());
		assertEquals("Token expired.", confirmationService.confirmUserAccount("123"));
	}

	@Test
	public void test_verifyUser_validEmail_positive() throws MessagingException, SesException, IOException {

		when(userDao.findByEmailIgnoreCase("new@example.com")).thenReturn(getUnconfirmedUser());
		assertEquals("Account created. Please check your email to verify your account.", confirmationService.verifyUser("new@example.com"));
	}

	@Test
	public void test_verifyUser_emailNotExist_negative() throws MessagingException, SesException, IOException {
		assertEquals("Email doesn't exist", confirmationService.verifyUser("test@example.com"));
	}

	@Test
	public void test_verifyUser_userAlreadyVerified_negative() throws MessagingException, SesException, IOException {
		when(confirmationDao.findConfirmationByUser(getConfirmedUser())).thenReturn(validToken(true));
		when(userDao.findByEmailIgnoreCase("verified@example.com")).thenReturn(getConfirmedUser());
		assertEquals("User already confirmed!", confirmationService.verifyUser("verified@example.com"));
	}
	
	private User getUnconfirmedUser() {
		return createUser("2");
	}
	
	private User getConfirmedUser() {
		return createUser("1");
	}
	
	private User createUser(String confirmedCode) {
		User user = new User();
		user.setUserId(confirmedCode);
		return user;
	}
	
	private Confirmation validToken(Boolean alreadyConfirmed) {
		Confirmation test = new Confirmation();
		test.setConfirmationToken("validtoken");
		test.setIsConfirmed(alreadyConfirmed);
		test.setCreatedDate(Timestamp.from(Instant.now()));
		User user = new User();
		test.setUser(user);
		return test;
	}
	
	private Confirmation expiredToken() {
		Confirmation test = new Confirmation();
		test.setConfirmationToken("123");
		test.setIsConfirmed(false);
		test.setCreatedDate(Timestamp.from(Instant.now().minus(100, ChronoUnit.DAYS)));
		User user = new User();
		test.setUser(user);
		return test;
	}
}