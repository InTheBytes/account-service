package com.inthebytes.accountservice.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfirmationServiceTest {

	@Autowired
	ConfirmationService confirmationService;

	@org.junit.Test
	public void test_confirmUserAccount_confirm_positive() {
		assertEquals(200, confirmationService.confirmUserAccount("validtoken").getStatusCodeValue());
	}

	@org.junit.Test
	public void test_confirmUserAccount_expired_negative() {
		assertEquals(401, confirmationService.confirmUserAccount("123").getStatusCodeValue());
	}


	@org.junit.Test
	public void test_verifyUser_validEmail_positive() {
		assertEquals(201, confirmationService.verifyUser("mosaab.aljarih@smoothstack.com").getStatusCodeValue());
	}

	@org.junit.Test
	public void test_verifyUser_emailNotExist_negative() {
		assertEquals(401, confirmationService.verifyUser("test@smoothstack.com").getStatusCodeValue());
	}

	@org.junit.Test
	public void test_verifyUser_userAlreadyVerified_negative() {
		assertEquals(200, confirmationService.verifyUser("verified@smoothstack.com").getStatusCodeValue());
	}
}