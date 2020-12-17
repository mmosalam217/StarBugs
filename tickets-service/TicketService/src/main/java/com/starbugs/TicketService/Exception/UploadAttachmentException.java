package com.starbugs.TicketService.Exception;

import java.io.IOException;

public class UploadAttachmentException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public UploadAttachmentException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
