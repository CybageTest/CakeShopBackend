package com.littlejoys.exception;

public class InvalidOldPasswordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOldPasswordException(String message) {
		super(message);
	}
}
