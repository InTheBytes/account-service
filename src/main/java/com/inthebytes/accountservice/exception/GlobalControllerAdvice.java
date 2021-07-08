package com.inthebytes.accountservice.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ObjectNotFoundException.class, UserDoesNotExistException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleObjectNotFound(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleBadParams(MissingServletRequestParameterException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleOtherException(Exception ex) {
		return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NotAuthorizedException.class)
	protected ResponseEntity<Object> handleNotAuthorized(
			NotAuthorizedException exc, WebRequest request) {
		
		String body = "Your account does not have permissions for this operation";
        return handleExceptionInternal(exc,	body, 
          new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}
	

}
