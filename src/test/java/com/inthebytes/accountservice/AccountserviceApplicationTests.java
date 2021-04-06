package com.inthebytes.accountservice;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inthebytes.accountservice.model.UserAccount;
import com.inthebytes.accountservice.service.RegistrationService;

@SpringBootTest
class AccountserviceApplicationTests {
	
	@Autowired
	RegistrationService service;

	@Test
	void contextLoads() {
	}
	
	@Test
	void registrationTestOldUser() throws SQLException {
		UserAccount testUser = new UserAccount();
		testUser.setFirstName("Michael");
		testUser.setLastName("Acker");
		testUser.setEmail("michael.acker@smoothstack.com");
		testUser.setPhone("4434225305");
		testUser.setUsername("macker");
		testUser.setPassword("password");
		testUser.setUserRole(testUser.ADMIN_ROLE);
		assertNull(service.RegisterNewAccount(testUser));
	}
	
	@Test
	void registrationTestNewUser() throws SQLException {
		UserAccount testUser = new UserAccount();
		testUser.setFirstName("Test");
		testUser.setLastName("User");
		double random = Math.random();
		String randomName = Integer.toString((int)(100000*random));
		testUser.setEmail(randomName + "@smoothstack.com");
		testUser.setPhone("5555555555");
		testUser.setUsername(randomName + "testuser");
		testUser.setPassword("password");
		testUser.setUserRole(testUser.CUSTOMER_ROLE);
		UserAccount returned = service.RegisterNewAccount(testUser);
		assertTrue(returned.getUserId() > 0);
	}

}
