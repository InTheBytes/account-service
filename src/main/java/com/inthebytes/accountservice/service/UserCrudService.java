package com.inthebytes.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.dto.PasswordChangeDto;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.entity.User;
import com.inthebytes.accountservice.exception.NotAuthorizedException;
import com.inthebytes.accountservice.exception.UserDoesNotExistException;

@Service
@Transactional
public class UserCrudService {
	
	@Autowired
	private UserMapperService mapper;

	@Autowired
	private UserDao repo;
	
	public UserDto findByUsername(String username) {
		return mapper.convert(repo.findByUsername(username));
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
			throw new UserDoesNotExistException("User ID "+userId+" not found");
		else {
			user.setUserId(userId);
			String password = userEntity.getPassword();
			userEntity = mapper.convert(user);
			userEntity.setPassword(password);
			userEntity = repo.save(userEntity);
			UserDto result = mapper.convert(userEntity);
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
			return mapper.convert(repo.save(user));
		}
	}
}
