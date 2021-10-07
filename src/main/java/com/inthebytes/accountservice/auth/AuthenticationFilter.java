package com.inthebytes.accountservice.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.accountservice.dto.BareUserDto;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;

	public AuthenticationFilter(AuthenticationManager authManager) {
		super();
		this.authManager = authManager;

		setFilterProcessesUrl("/user/login");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		BareUserDto credentials = null;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), BareUserDto.class);
		} catch (IOException e) {
			return null;
		}
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					credentials.getUsername(),
					credentials.getPassword(),
					new ArrayList<>());
		
		try {
			return authManager.authenticate(authToken);
		} catch (NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		LoginPrincipal prin = (LoginPrincipal) authResult.getPrincipal();
		String authorities = prin.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		String token = JWT.create()
				.withSubject(prin.getUsername())
				.withClaim(JwtProperties.AUTHORITIES_KEY, authorities)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
	}
}
