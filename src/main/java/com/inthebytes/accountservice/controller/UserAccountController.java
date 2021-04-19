package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserAccountController {

	@Autowired
	private ConfirmationService confirmationService;

	@PutMapping(path="/confirm-account", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> putUserConfirmation(@RequestParam("token") String confirmationToken) {
		String result = confirmationService.confirmUserAccount(confirmationToken);

		switch (result) {
			case "Account confirmed!":
				return new ResponseEntity<>("Account confirmed!", HttpStatus.OK);
			case "Token expired.":
				return new ResponseEntity<>("Token expired.", HttpStatus.UNAUTHORIZED);
			case "Invalid verification token.":
				return new ResponseEntity<>("Invalid verification token.", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<>("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// TODO: Remove this resource, and call verifyUser after user registration instead
	@PutMapping(value="/verify", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> putVerifyUser(@RequestParam("email") String email) {
		String result = confirmationService.verifyUser(email);

		switch (result) {
			case "Email doesn't exist":
				return new ResponseEntity<>("Email doesn't exist", HttpStatus.UNAUTHORIZED);
			case "User already confirmed!":
				return new ResponseEntity<>("User already confirmed!", HttpStatus.OK);
			case "Account created. Please check your email to verify your account.":
				return new ResponseEntity<>("Account created. Please check your email to verify your account.", HttpStatus.CREATED);
			default:
				return new ResponseEntity<>("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
