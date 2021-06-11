package com.inthebytes.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.entity.User;

@Service
public class UserCrudService {
	
	@Autowired
	private UserMapperService mapper;

	@Autowired
	private UserDao repo;
	
	private List<UserDto> readUsers() {
		List<UserDto> users = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		return (users.size() > 0) ? users : null;
	}
	
	public Page<UserDto> readUsers(Integer page, Integer pageSize) {
		return repo.findAll(PageRequest.of(page, pageSize)).map((x) -> mapper.convert(x));
	}
	
	public Page<UserDto> readUsersByActive(Boolean active, Integer page, Integer pageSize) {
		return repo.findByActive(active, PageRequest.of(page, pageSize)).map((x) -> mapper.convert(x));
	}
	
	public UserDto readUser(String userId) {
		User user = repo.findByUserId(userId);
		return (user == null) ? null : mapper.convert(user);
	}
	
	public UserDto updateUser(UserDto user, String userId) {
		User userEntity = repo.findByUserId(userId);
		if (userEntity == null)
			return null;
		else {
			user.setUserId(userId);
			String password = userEntity.getPassword();
			userEntity = mapper.convert(user);
			userEntity.setPassword(password);
			return mapper.convert(repo.save(userEntity));
		}
	}
	
	public UserDto deleteUser(String userId) {
		User user = repo.findByUserId(userId);
		if (user == null)
			return null;
		else {
			user.setActive(false);
			return mapper.convert(repo.save(user));
		}
	}
}
