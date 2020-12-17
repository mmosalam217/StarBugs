package com.starbugs.workspace.Exception;

public class EntityExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;

	public EntityExistsException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	

}
