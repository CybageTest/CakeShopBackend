package com.littlejoys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<String> handleException(ResourceAlreadyExistException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleException(ResourceNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceCannotBeNullException.class)
	public ResponseEntity<String> handleException(ResourceCannotBeNullException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleException(InvalidCredentialsException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(InvalidOldPasswordException.class)
	public ResponseEntity<String> handleException(InvalidOldPasswordException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserDisabledException.class)
	public ResponseEntity<String> handleException(UserDisabledException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}

}
