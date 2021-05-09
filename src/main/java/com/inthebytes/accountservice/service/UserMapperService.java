package com.inthebytes.accountservice.service;

import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dto.RoleDto;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.entity.Role;
import com.inthebytes.accountservice.entity.User;

@Service
public class UserMapperService {

	public UserDto convert(User user) {
		UserDto result = new UserDto(convert(user.getRole()), user.getUsername(), user.getEmail(), user.getActive());
		result.setUserId(user.getUserId());
		result.setPhone(user.getPhone());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		return result;
	}
	
	public User convert(UserDto user) {
		User result = new User();
		if (user.getUserId() != null) result.setUserId(user.getUserId());
		if (user.getUsername() != null) result.setUsername(user.getUsername());
		if (user.getEmail() != null) result.setEmail(user.getEmail());
		if (user.getPhone() != null) result.setPhone(user.getPhone());
		if (user.getIsActive() != null) result.setActive(user.getIsActive());
		if (user.getFirstName() != null) result.setFirstName(user.getFirstName());
		if (user.getLastName() != null) result.setLastName(user.getLastName());
		if (user.getRole() != null) result.setRole(convert(user.getRole()));
		return result;
	}
	
	public RoleDto convert(Role role) {
		return new RoleDto(role.getRoleId(), role.getName());
	}
	
	public Role convert(RoleDto role) {
		Role result = new Role();
		result.setRoleId(role.getRoleId());
		result.setName(role.getName());
		return result;
	}
}
