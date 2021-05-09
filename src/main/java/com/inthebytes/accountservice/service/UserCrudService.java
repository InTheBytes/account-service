package com.inthebytes.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<UserDto> readUsers() {
		List<UserDto> users = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		return (users.size() > 0) ? users : null;
	}
	
	public List<List<UserDto>> readUsers(Integer pageSize) {
		List<UserDto> manuscript = readUsers();
		Map<Integer, List<UserDto>> pages = manuscript.stream().collect(Collectors.groupingBy(x -> manuscript.indexOf(x)/pageSize));
		List<List<UserDto>> paginated = new ArrayList<List<UserDto>>(pages.values());
		return paginated;
	}
	
	public UserDto readUser(Long userId) {
		User user = repo.findByUserId(userId);
		return (user == null) ? null : mapper.convert(user);
	}
	
	public UserDto updateUser(UserDto user) {
		User userEntity = repo.findByUserId(user.getUserId());
		if (userEntity == null)
			return null;
		else {
			userEntity = mapper.convert(user);
			return mapper.convert(repo.save(userEntity));
		}
	}
	
	public UserDto updateUser(UserDto user, Long userId) {
		User userEntity = repo.findByUserId(userId);
		if (userEntity == null)
			return null;
		else {
			user.setUserId(userId);
			userEntity = mapper.convert(user);
			return mapper.convert(repo.save(userEntity));
		}
	}
	
	public UserDto deleteUser(Long userId) {
		User user = repo.findByUserId(userId);
		if (user == null)
			return null;
		else {
			user.setActive(false);
			return mapper.convert(repo.save(user));
		}
	}
}
