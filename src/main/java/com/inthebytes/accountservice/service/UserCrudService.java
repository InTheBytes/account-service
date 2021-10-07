package com.inthebytes.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.exception.UserDoesNotExistException;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;

@Service
@Transactional
public class UserCrudService {

	@Autowired
	private UserDao repo;
	
	public UserDto findByUsername(String username) {
		return UserDto.convert(repo.findByUsername(username));
	}
	
	public Page<UserDto> readUsers(Integer page, Integer pageSize) {
		return UserDto.convert(repo.findAll(PageRequest.of(page, pageSize)));
	}
	
	public Page<UserDto> readUsersByActive(Boolean active, Integer page, Integer pageSize) {
		return UserDto.convert(repo.findByActive(active, PageRequest.of(page, pageSize)));
	}
	
	public UserDto readUser(String userId) {
		User user = repo.findByUserId(userId);
		return (user == null) ? null : UserDto.convert(user);
	}
	
	public UserDto updateUser(UserDto user, String userId) {
		User userEntity = repo.findByUserId(userId);
		if (userEntity == null)
			throw new UserDoesNotExistException("User ID "+userId+" not found");
		else {
			user.setUserId(userId);
			String password = userEntity.getPassword();
			userEntity = user.convert();
			userEntity.setPassword(password);
			userEntity = repo.save(userEntity);
			UserDto result = UserDto.convert(userEntity);
			if (result.equals(user)) {
				return result;
			} else {
				throw new RuntimeException("Failed to save the new user details");
			}
		}
	}
	
	public UserDto deleteUser(String userId) {
		User user = repo.findByUserId(userId);
		if (user == null)
			throw new UserDoesNotExistException("User ID "+userId+" not found");
		else {
			user.setActive(false);
			return UserDto.convert(repo.save(user));
		}
	}
}
