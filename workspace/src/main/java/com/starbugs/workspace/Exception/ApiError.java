package com.starbugs.workspace.Exception;

import java.time.LocalDateTime;


public class ApiError {

	private int status;
	private LocalDateTime timestamp;
	private String message;
	
	public ApiError(int status, String message, LocalDateTime timestamp) {
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
