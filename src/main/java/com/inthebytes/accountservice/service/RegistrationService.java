package com.inthebytes.accountservice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;

@Service
public class RegistrationService {
	
	@Autowired
	UserDao repo;

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
				return UserDto.convert(repo.save(newUser));
			}
	}
}
