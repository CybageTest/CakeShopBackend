package com.littlejoys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceCannotBeNullException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceCannotBeNullException(String message) {
		super(message);
	}

	public ResourceCannotBeNullException() {
		// TODO Auto-generated constructor stub
	}
}
