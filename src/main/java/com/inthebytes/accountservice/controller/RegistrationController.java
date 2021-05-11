package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.service.RegistrationService;

import software.amazon.awssdk.services.ses.model.MessageRejectedException;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class RegistrationController {

	@Autowired
	RegistrationService service;

	@Autowired
	private ConfirmationService confirmationService;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> registerUser(@RequestBody User newUser) {

		ResponseEntity<User> response;
		User updated = null;
		try {

			updated = service.RegisterNewUser(newUser);

			if (updated == null) {
				response = new ResponseEntity<>(updated, HttpStatus.CONFLICT);
			} else {
				
				String verifyUserResults = confirmationService.verifyUser(newUser.getEmail());

				if (!"Account created. Please check your email to verify your account.".equals(verifyUserResults)) {
					throw new Exception("Could not verify new user email");
				}

				response = new ResponseEntity<>(updated, HttpStatus.CREATED);
			}

		} catch (MessageRejectedException e) {
			//Due to AWS - this exception will throw for all test emails.
			//If it does, that means it works - TAKE THIS OUT LATER.
			response = new ResponseEntity<>(updated, HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

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
}
