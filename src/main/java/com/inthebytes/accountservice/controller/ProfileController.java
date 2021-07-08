package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.dto.PasswordChangeDto;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.service.UserCrudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user/profile")
public class ProfileController {
	
	@Autowired
	private UserCrudService userService;

	@Operation(summary = "Get user profile by including token", description = "", tags = { "profile" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@GetMapping
	public ResponseEntity<UserDto> getProfile(@RequestAttribute String username) {
		UserDto result = userService.findByUsername(username);
		return (result == null) ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() : ResponseEntity.ok().body(result);
	}
	
	@Operation(summary = "Change profile password using auth token for identity", description = "", tags = { "profile", "passsword" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Invalid authorization", content = @Content)
	})
	@PutMapping("/password")
	public ResponseEntity<?> editPassword(
			@RequestAttribute("username") String username, 
			@RequestBody PasswordChangeDto passChange) {
		if (userService.changePassword(username, passChange)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
	}
}
