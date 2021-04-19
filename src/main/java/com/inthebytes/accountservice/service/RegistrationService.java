package com.inthebytes.accountservice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.UserAccountDAO;
import com.inthebytes.accountservice.model.UserAccount;

@Service
public class RegistrationService {
	
	@Autowired
	UserAccountDAO repo;

	public UserAccount RegisterNewAccount(UserAccount account) throws SQLException {
		
			List<UserAccount> accounts = repo.findAll();
			List<String> emails = new ArrayList<String>();
			List<String> usernames = new ArrayList<String>();
			accounts.forEach(a -> {emails.add(a.getEmail()); usernames.add(a.getUsername());});
			if (emails.contains(account.getEmail()) || usernames.contains(account.getUsername())) {
				return null;
			} else {
				String plaintext = account.getPassword();
				account.setPassword(new BCryptPasswordEncoder().encode(plaintext));
				return repo.save(account);
			}
	}
}
