package com.starbugs.Core.Exception;

public class BadTokenException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public BadTokenException(String message) {
		super(message);
		this.message = message;
		
	}
	
	public String getMessage() {
		return this.message;
	}
}
