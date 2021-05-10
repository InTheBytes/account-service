package com.inthebytes.accountservice.service;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.services.ses.model.SesException;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfirmationServiceTest {

	@Autowired
	ConfirmationService confirmationService;

	@Test
	public void test_confirmUserAccount_confirm_positive() {
		// Need to change data.sql to reflect correct date
		assertEquals("Account confirmed!", confirmationService.confirmUserAccount("validtoken"));
	}

	@Test
	public void test_confirmUserAccount_expired_negative() {
		assertEquals("Token expired.", confirmationService.confirmUserAccount("123"));
	}

	@org.junit.Test
	public void test_verifyUser_validEmail_positive() throws MessagingException, SesException, IOException {
		assertEquals("Account created. Please check your email to verify your account.", confirmationService.verifyUser("new@example.com"));
	}

	@org.junit.Test
	public void test_verifyUser_emailNotExist_negative() throws MessagingException, SesException, IOException {
		assertEquals("Email doesn't exist", confirmationService.verifyUser("test@example.com"));
	}

	@org.junit.Test
	public void test_verifyUser_userAlreadyVerified_negative() throws MessagingException, SesException, IOException {
		assertEquals("User already confirmed!", confirmationService.verifyUser("verified@example.com"));
	}
}