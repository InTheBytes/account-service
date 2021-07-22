package com.inthebytes.accountservice.exception;

public class TokenDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 3983386703073997458L;

	public TokenDoesNotExistException() {
	}

	public TokenDoesNotExistException(String arg) {
		super(arg);
	}

}
