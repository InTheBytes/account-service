package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.model.UserAccount;
import com.inthebytes.accountservice.service.RegistrationService;


@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	RegistrationService service;
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserAccount> registerUser(@RequestBody UserAccount account) {
		
		ResponseEntity<UserAccount> response;
		try {
				UserAccount updated = service.RegisterNewAccount(account);
			if (updated == null) {
				response = new ResponseEntity<UserAccount>(updated, HttpStatus.CONFLICT);
			} else {
				response = new ResponseEntity<UserAccount>(updated, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			response = new ResponseEntity<UserAccount>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
