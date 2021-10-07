package com.inthebytes.accountservice.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;
import com.inthebytes.stacklunch.security.JwtProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;

@Service
public class LogoutService implements LogoutHandler {

	@Autowired
	AuthorizationRepository authorizationDao;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			String token = request.getHeader(JwtProperties.HEADER_STRING)
					.substring(JwtProperties.TOKEN_PREFIX.length());

			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
					.build();
			DecodedJWT jwt = verifier.verify(token);

			if (!authorizationDao.existsById(jwt.getToken())) {
				Authorization authorization = new Authorization();
				authorization.setToken(token);
				authorization.setExpirationDate(new Timestamp(jwt.getExpiresAt().getTime()));

				authorizationDao.save(authorization);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				System.out.println("Token already invalidated");
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (JWTVerificationException e) {
			System.err.println("Invalid token");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
