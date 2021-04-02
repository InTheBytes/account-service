package com.inthebytes.accountservice.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.accountservice.model.LoginViewModel;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;

	public AuthenticationFilter(AuthenticationManager authManager) {
		super();
		this.authManager = authManager;
	}
	
	/**
	 * Triggers with POST
	 * Pass in {"username":"user", "password":"pass"} template
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		LoginViewModel credentials;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					credentials.getUsername(),
					credentials.getPassword(),
					new ArrayList<>());
		
		Authentication auth = authManager.authenticate(authToken);
		return auth;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
