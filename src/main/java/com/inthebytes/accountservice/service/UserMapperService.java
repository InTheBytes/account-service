package com.inthebytes.accountservice.service;

import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dto.RoleDto;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.entity.Role;
import com.inthebytes.accountservice.entity.User;

@Service
public class UserMapperService {

	public UserDto convert(User user) {
		UserDto result = new UserDto();
		result.setUserId(user.getUserId());
		result.setUsername(user.getUsername());
		result.setPassword(user.getPassword());
		result.setEmail(user.getEmail());
		result.setPhone(user.getPhone());
		result.setIsActive(user.getActive());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setRole(convert(user.getRole()));
		return result;
	}
	
	public User convert(UserDto user) {
		User result = new User();
		result.setUserId(user.getUserId());
		result.setUsername(user.getUsername());
		result.setPassword(user.getPassword());
		result.setEmail(user.getEmail());
		result.setPhone(user.getPhone());
		result.setActive(user.getIsActive());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setRole(convert(user.getRole()));
		return result;
	}
	
	public RoleDto convert(Role role) {
		RoleDto result = new RoleDto();
		result.setRoleId(role.getRoleId());
		result.setName(role.getName());
		return result;
	}
	
	public Role convert(RoleDto role) {
		Role result = new Role();
		result.setRoleId(role.getRoleId());
		result.setName(role.getName());
		return result;
	}
}
