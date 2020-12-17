package com.starbugs.Core.DTO;


public class ResetPasswordTokenResponse {

	private String token;
	private String message;
	


	public ResetPasswordTokenResponse() {
	}

	public ResetPasswordTokenResponse(String token, String message) {
		this.token = token;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
