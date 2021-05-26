package com.inthebytes.accountservice.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.accountservice.controller.RegistrationController;
import com.inthebytes.accountservice.service.ConfirmationService;
import com.inthebytes.accountservice.service.RegistrationService;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc
public class RegistrationControllerTest {

	@MockBean
	RegistrationService service;

	@MockBean
	private ConfirmationService confirmationService;
	
	@Autowired
	MockMvc mock;
	
	@Test
	public void registerUser() {
	}
	
	@Test
	public void registerInvalidUser() {
		
	}

	@Test
	public void putUserConfirmation() {
	}
	
	@Test
	public void putInvalidUserConfirmation() {
		
	}

}
