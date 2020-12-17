package com.starbugs.TicketService.Exception;

public class EntityNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	private final String message;
	public EntityNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	
	
	
}
