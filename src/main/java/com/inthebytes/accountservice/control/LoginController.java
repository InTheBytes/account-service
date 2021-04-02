package com.inthebytes.accountservice.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@GetMapping("login")
	public String loginView() {return "";}
	
}
