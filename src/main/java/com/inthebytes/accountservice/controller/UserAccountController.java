package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final ConfirmationService confirmationService;

	public UserAccountController(ConfirmationService confirmationService) {
		this.confirmationService = confirmationService;
	}

	@PutMapping(path="/confirm-account", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> putUserConfirmation(@RequestParam("token")String confirmationToken) {
		return confirmationService.confirmUserAccount(confirmationToken);
	}

	@PutMapping(value="/verify", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> putVerifyUser(@RequestParam("email") String email) {
		return confirmationService.verifyUser(email);
	}
}
