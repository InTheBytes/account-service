package com.inthebytes.accountservice.service;

import com.inthebytes.accountservice.dao.UserDao;
import com.inthebytes.accountservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.inthebytes.accountservice.login.LoginPrincipal;

@Service
public class LoginDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		LoginPrincipal loginPrin = new LoginPrincipal(user);
		return loginPrin;
	}
}