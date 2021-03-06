package com.inthebytes.accountservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.service.UserCrudService;
import com.inthebytes.stacklunch.data.user.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "The user API")
public class UserAccountController {

	@Autowired
	private UserCrudService userService;
	

	@Operation(summary = "Get user by user ID", description = "", tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@GetMapping(value="/{user-id}")
	public ResponseEntity<UserDto> getUser(@PathVariable("user-id") String userId) {
		UserDto result = userService.readUser(userId);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Get page of users", 
			description = "Returns a page of users with optional params for page number, page size, and active"
					+ "if nothing is provided for active, it will return all users. The parameters takes 'true' or 'false'", 
			tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class, type = "List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class, type = "List"))
			})
	})
	@GetMapping(value="")
	public ResponseEntity<Page<UserDto>> getAllUsers(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "active", required = false, defaultValue = "_") String active)
			 {
		
		Page<UserDto> users;
		if ("true".equals(active) || "false".equals(active)) {
			users = userService.readUsersByActive(Boolean.parseBoolean(active), page, pageSize);
		} else {
			users = userService.readUsers(page, pageSize);
		}
		
		if (users == null || users.getContent().size() <= 0)
			return ResponseEntity.noContent().build();
		else {
			return ResponseEntity.ok().body(users);
		}
	}

	@Operation(summary = "Update an existing user by ID", description = "", tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@PutMapping(value="/{user-id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("user-id") String userId, @Valid @RequestBody UserDto info) {
		UserDto result = userService.updateUser(info, userId);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Delete an existing user by ID", description = "", tags = { "user" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = UserDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@DeleteMapping(value="/{user-id}")
	public ResponseEntity<UserDto> deactiveUser(@PathVariable("user-id") String userId) {
		UserDto result = userService.deleteUser(userId);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
	}
	
}
