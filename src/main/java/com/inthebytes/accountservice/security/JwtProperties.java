package com.inthebytes.accountservice.security;

public class JwtProperties {
	public static final String SECRET = "StackLunchBunch";
	public static final int EXPIRATION_TIME = 86400000 * 1;
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";

}
