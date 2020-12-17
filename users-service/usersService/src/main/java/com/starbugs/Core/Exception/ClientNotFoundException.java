package com.starbugs.Core.Exception;

public class ClientNotFoundException extends Exception{

	
	private static final long serialVersionUID = 1L;
	private String message;
	public ClientNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
