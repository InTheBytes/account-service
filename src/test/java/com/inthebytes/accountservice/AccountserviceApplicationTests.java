package com.inthebytes.accountservice;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.accountservice.dao.UserAccountDAO;
import com.inthebytes.accountservice.model.UserAccount;
import com.inthebytes.accountservice.service.RegistrationService;

@AutoConfigureMockMvc
@SpringBootTest
class AccountserviceApplicationTests {
	
	//@Autowired
	//RegistrationService service;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private RegistrationService service;
	
	@MockBean
	private UserAccountDAO repo;

	@Test
	void contextLoads() {
	}
	
	/*
	@Test
	void registrationTestOldUser() throws SQLException {
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
	*/
	
	private UserAccount makeUser() {
		UserAccount testUser = new UserAccount();
		testUser.setFirstName("Test");
		testUser.setLastName("User");
		testUser.setEmail("test.user@stacklunch.com");
		testUser.setPhone("5555557653");
		testUser.setUsername("test_user");
		testUser.setPassword("testPassword1");
		testUser.setUserRole(testUser.CUSTOMER_ROLE);
		return testUser;
	}

}
