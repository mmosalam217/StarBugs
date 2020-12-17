package com.starbugs.Core.Exception;

public class UsernameConflictException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public UsernameConflictException(String message) {
		super(message);
		this.message = message;
	}
	
	
	public String getMessage() {
		return this.message;
	}
	

}
