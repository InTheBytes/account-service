package com.inthebytes.accountservice.controller;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.model.UserAccount;
import com.inthebytes.accountservice.service.RegistrationService;


@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	RegistrationService service;
	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public UserAccount registerUser(@PathVariable UserAccount account) throws SQLException {
		return service.RegisterNewAccount(account);
	}
}
