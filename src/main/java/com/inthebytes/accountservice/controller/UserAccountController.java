package com.inthebytes.accountservice.controller;

import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.service.UserCrudService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserAccountController {

	@Autowired
	private UserCrudService userService;
	
	@GetMapping(value="/{user-id}")
	public ResponseEntity<UserDto> getUser(@PathVariable("user-id") Long userId) {
		UserDto result = userService.readUser(userId);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value="")
	public ResponseEntity<List<UserDto>> getAllUsers(
			@RequestParam("page-size") Integer pageSize,
			@RequestParam("page") Integer page) {
		List<List<UserDto>> users = userService.readUsers(pageSize);
		if (users == null || users.size() <= 0)
			return ResponseEntity.noContent().build();
		else {
			HttpHeaders headers = new HttpHeaders();
			headers.set("page", Integer.toString(page));
			headers.set("total-pages", Integer.toString(users.size()));
			headers.set("Access-Control-Expose-Headers", "page, total-pages");
			return ResponseEntity.ok().headers(headers).body(users.get(page-1));
		}
	}
	
	@GetMapping(value="/active")
	public ResponseEntity<List<UserDto>> getActiveUsers(
			@RequestParam("page-size") Integer pageSize,
			@RequestParam("page") Integer page) {
		List<List<UserDto>> users = userService.readActiveUsers(pageSize);
		if (users == null || users.size() <= 0)
			return ResponseEntity.noContent().build();
		else {
			HttpHeaders headers = new HttpHeaders();
			headers.set("page", Integer.toString(page));
			headers.set("total-pages", Integer.toString(users.size()));
			headers.set("Access-Control-Expose-Headers", "page, total-pages");
			return ResponseEntity.ok().headers(headers).body(users.get(page-1));
		}
	}
	
	@PutMapping(value="/{user-id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("user-id") Long userId, @Valid @RequestBody UserDto info) {
		UserDto result = userService.updateUser(info, userId);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value="/{user-id}")
	public ResponseEntity<UserDto> deactiveUser(@PathVariable("user-id") Long userId) {
		UserDto result = userService.deleteUser(userId);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
	}
	
}
