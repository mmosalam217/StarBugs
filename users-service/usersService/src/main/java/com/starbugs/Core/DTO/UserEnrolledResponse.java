package com.starbugs.Core.DTO;

import org.springframework.http.HttpStatus;

public class UserEnrolledResponse {
	private int status;
	private String message;
	private HttpStatus statusText;
	
	public UserEnrolledResponse() {
		
	}

	public UserEnrolledResponse(int status, String message, HttpStatus statusText) {
		super();
		this.status = status;
		this.message = message;
		this.statusText = statusText;
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

	public HttpStatus getStatusText() {
		return statusText;
	}

	public void setStatusText(HttpStatus statusText) {
		this.statusText = statusText;
	}
	
	

}
