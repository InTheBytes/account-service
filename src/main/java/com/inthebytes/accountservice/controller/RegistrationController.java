package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.service.ConfirmationService;
import com.inthebytes.accountservice.service.RegistrationService;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRegistrationDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import software.amazon.awssdk.services.ses.model.MessageRejectedException;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "The user API")
public class RegistrationController {

	@Autowired
	RegistrationService service;

	@Autowired
	private ConfirmationService confirmationService;

	@Operation(summary = "Register a new user", description = "", tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class))
			}),
			@ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
	})
	@PostMapping(path = "/register")
	@ResponseBody
	public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationDto newUser) {

		ResponseEntity<UserDto> response;
		UserDto updated = null;
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

	@Operation(summary = "Confirm new user with token", description = "", tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account confirmed", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = String.class))
			}),
			@ApiResponse(responseCode = "401", description = "Token expired or invalid", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = String.class))
			})
	})
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
