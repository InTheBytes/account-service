package com.inthebytes.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inthebytes.accountservice.dao.LoginUserDAO;
import com.inthebytes.accountservice.model.LoginUser;

@Service
public class LoginDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginUserDAO userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser user = userRepo.findUserByUsername(username);
		LoginPrinciple loginPrin = new LoginPrinciple(user);
		return loginPrin;
	}

}
