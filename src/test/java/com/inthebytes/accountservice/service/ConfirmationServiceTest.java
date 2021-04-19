package com.inthebytes.accountservice.service;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


	@Test
	public void test_verifyUser_validEmail_positive() {
		assertEquals("Account created. Please check your email to verify your account.", confirmationService.verifyUser("mosaab.aljarih@smoothstack.com"));
	}

	@Test
	public void test_verifyUser_emailNotExist_negative() {
		assertEquals("Email doesn't exist", confirmationService.verifyUser("test@smoothstack.com"));
	}

	@Test
	public void test_verifyUser_userAlreadyVerified_negative() {
		assertEquals("User already confirmed!", confirmationService.verifyUser("verified@smoothstack.com"));
	}
}