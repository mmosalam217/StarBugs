package com.starbugs.Core.Exception;


public class EmailVerificationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;

	
	
	public EmailVerificationException() {
		
	}


	public EmailVerificationException(String message) {
		super(message);
		this.message = message;
	}


	public String getMessage() {
		return message;
	}
	




}
