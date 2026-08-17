package com.hydromart.exception;

public class InvalidCredentialsException extends RuntimeException{
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
