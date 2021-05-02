package com.inthebytes.accountservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.dto.UserDto;

@Service
public class UserCrudService {

	@Autowired
	private UserDao repo;
	
	public Long createUser(UserDto user) {
		return null;
	}
	
	public List<Long> createUsers(List<UserDto> users) {
		return null;
	}
	
	public List<UserDto> readUsers() {
		return null;
	}
	
	public List<UserDto> readUsers(Integer pageSize, Integer pageNumber) {
		return null;
	}
	
	public UserDto readUser(Long userId) {
		return null;
	}
	
	public UserDto updateUser(UserDto user) {
		return null;
	}
	
	public UserDto updateUser(UserDto user, Long userId) {
		return null;
	}
	
	public UserDto deleteUser(Long userId) {
		return null;
	}
	
	public Boolean deleteUser(UserDto user) {
		return null;
	}
}
