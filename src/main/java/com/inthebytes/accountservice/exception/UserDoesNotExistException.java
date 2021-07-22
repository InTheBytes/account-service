package com.inthebytes.accountservice.exception;

public class UserDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 6386402153362767687L;

	public UserDoesNotExistException() {
		super();
	}

	public UserDoesNotExistException(String arg0) {
		super(arg0);
	}

}
