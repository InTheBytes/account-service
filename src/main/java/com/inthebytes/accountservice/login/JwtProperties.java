package com.inthebytes.accountservice.login;

public class JwtProperties {
	public static final String SECRET = "StackLunchBunch";
	public static final int EXPIRATION_TIME = 3600000 * 2; //2 hours
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authentication";
	public static final String AUTHORITIES_KEY = "InBytesAuth";

}
