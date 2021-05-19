package com.inthebytes.accountservice.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inthebytes.accountservice.dao.ConfirmationDao;
import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.Confirmation;
import com.inthebytes.accountservice.entity.User;

import software.amazon.awssdk.services.ses.model.SesException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmationServiceTest {
	
	@Mock
	UserDao userDao;

	@Mock
	ConfirmationDao confirmationDao;

	@Mock
	EmailSendService emailSendService;

	@InjectMocks
	ConfirmationService confirmationService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(confirmationDao.findByConfirmationToken("validtoken")).thenReturn(validToken(false));
		when(confirmationDao.findByConfirmationToken("123")).thenReturn(expiredToken());
		
		User confirmedUser = getUser(true);
		User unconfirmedUser = getUser(false);
		when(userDao.findByEmailIgnoreCase("new@example.com")).thenReturn(unconfirmedUser);
		when(userDao.findByEmailIgnoreCase("test@example.com")).thenReturn(null);
		when(userDao.findByEmailIgnoreCase("verified@example.com")).thenReturn(confirmedUser);
		
		when(confirmationDao.findConfirmationByUser(confirmedUser)).thenReturn(validToken(true));
		when(confirmationDao.findConfirmationByUser(unconfirmedUser)).thenReturn(null);
	}

	@Test
	public void test_confirmUserAccount_confisrm_positive() {
		// Need to change data.sql to reflect correct date
		assertEquals("Account confirmed!", confirmationService.confirmUserAccount("validtoken"));
	}

	@Test
	public void test_confirmUserAccount_expired_negative() {
		assertEquals("Token expired.", confirmationService.confirmUserAccount("123"));
	}

	@Test
	public void test_verifyUser_validEmail_positive() throws MessagingException, SesException, IOException {
		assertEquals("Account created. Please check your email to verify your account.", confirmationService.verifyUser("new@example.com"));
	}

	@Test
	public void test_verifyUser_emailNotExist_negative() throws MessagingException, SesException, IOException {
		assertEquals("Email doesn't exist", confirmationService.verifyUser("test@example.com"));
	}

	@Test
	public void test_verifyUser_userAlreadyVerified_negative() throws MessagingException, SesException, IOException {
		assertEquals("User already confirmed!", confirmationService.verifyUser("verified@example.com"));
	}
	
	private User getUser(Boolean alreadyConfirmed) {
		User user = new User();
		user.setUserId((alreadyConfirmed)? 1L : 2L);
		return user;
	}
	
	private Confirmation validToken(Boolean alreadyConfirmed) {
		Confirmation test = new Confirmation();
		test.setConfirmationToken("validtoken");
		test.setConfirmed(alreadyConfirmed);
		test.setCreatedDate(Timestamp.from(Instant.now()));
		User user = new User();
		test.setUser(user);
		return test;
	}
	
	private Confirmation expiredToken() {
		Confirmation test = new Confirmation();
		test.setConfirmationToken("123");
		test.setConfirmed(false);
		test.setCreatedDate(Timestamp.from(Instant.now().minus(100, ChronoUnit.DAYS)));
		User user = new User();
		test.setUser(user);
		return test;
	}
}