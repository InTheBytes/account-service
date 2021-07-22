package com.inthebytes.accountservice.exception;

public class MessagingFailedException extends RuntimeException {

	private static final long serialVersionUID = -3971369472911781845L;

	public MessagingFailedException() {
	}

	public MessagingFailedException(String arg) {
		super(arg);
	}

}
