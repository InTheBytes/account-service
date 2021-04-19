package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.service.RegistrationService;


@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	RegistrationService service;
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> registerUser(@RequestBody User newUser) {
		
		ResponseEntity<User> response;
		try {
				User updated = service.RegisterNewUser(newUser);
			if (updated == null) {
				response = new ResponseEntity<User>(updated, HttpStatus.CONFLICT);
			} else {
				response = new ResponseEntity<User>(updated, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			response = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
