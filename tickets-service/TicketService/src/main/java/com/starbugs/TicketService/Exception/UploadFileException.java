package com.starbugs.TicketService.Exception;

public class UploadFileException extends Exception{
	private static final long serialVersionUID = 1L;
	private String message;
	
	public UploadFileException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	

}
