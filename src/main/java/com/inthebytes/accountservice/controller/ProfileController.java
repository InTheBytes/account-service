package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@Operation(summary = "Change profile password using password confirmation token", description = "", tags = { "profile", "passsword" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful password change operation", content = @Content),
			@ApiResponse(responseCode = "404", description = "Token not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Token parameter not included", content= @Content)
	})
	@PutMapping("/password")
	public ResponseEntity<?> editPassword(
			@RequestParam(name="token", required=true) String token,
			@RequestBody String newPassword) {
		//TODO: GENERATE TOKEN
		return null;
	}
	
	@Operation(summary = "Create a token to send via email for password changes", description="", tags = {"token", "profile", "password"})
	@ApiResponses(value = {
			@ApiResponse(responseCode="201", description="Token Successfully created", content = @Content),
			@ApiResponse(responseCode="404", description="username or email not found", content=@Content),
			@ApiResponse(responseCode="400", description="Neither username nor email parameters were included", content=@Content)
	})
	@GetMapping("/password/token")
	public ResponseEntity<?> generatePassChangeToken(
			@RequestParam(name="username", required=false, defaultValue="") String username,
			@RequestParam(name="email", required=false, defaultValue="") String email
			) {
		if (username.length() == 0 && email.length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else if (username.length() == 0) {
			//TODO: GENERATE TOKEN USING EMAIL
			return null;
		} else {
			//TODO: GENERATE TOKEN USING USERNAME
			return null;
		}
	}
}
