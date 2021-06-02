package com.inthebytes.accountservice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.dto.UserDto;
import com.inthebytes.accountservice.entity.User;

@Service
public class RegistrationService {
	
	@Autowired
	UserDao repo;
	
	@Autowired
	UserMapperService mapper;

	public UserDto RegisterNewUser(User newUser) throws SQLException {
		
			List<User> accounts = repo.findAll();
			List<String> emails = new ArrayList<String>();
			List<String> usernames = new ArrayList<String>();
			accounts.forEach(a -> {emails.add(a.getEmail()); usernames.add(a.getUsername());});
			if (emails.contains(newUser.getEmail()) || usernames.contains(newUser.getUsername())) {
				return null;
			} else {
				String plaintext = newUser.getPassword();
				newUser.setPassword(new BCryptPasswordEncoder().encode(plaintext));
				return mapper.convert(repo.save(newUser));
			}
	}
}
