package com.starbugs.TicketService.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiError {
	private int status;
	private HttpStatus statusText;
	private String message;
	private LocalDateTime timestamp;
	
	public ApiError(int status, HttpStatus statusText, String message, LocalDateTime timestamp) {
		this.status = status;
		this.statusText = statusText;
		this.message = message;
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public HttpStatus getStatusText() {
		return statusText;
	}
	public void setStatusText(HttpStatus statusText) {
		this.statusText = statusText;
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
