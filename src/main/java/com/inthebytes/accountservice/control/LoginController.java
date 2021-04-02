package com.inthebytes.accountservice.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.accountservice.dao.LoginUserDAO;
import com.inthebytes.accountservice.model.LoginUser;

@RestController
public class LoginController {
	
	@Autowired
	LoginUserDAO loginDao;
//
//	@PostMapping("/login")
//	public LoginUser authenticateUser() {
//		
//	}
	
	
}
