package com.inthebytes.accountservice.exception;

public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 5122789883336276015L;

	public NotAuthorizedException() {
		super();
	}

	public NotAuthorizedException(String message) {
		super(message);
	}

}
