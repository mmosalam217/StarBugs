package com.starbugs.Mailer.services;

public class VerificationMailDetails {
	
	private String username;
	
	private String email;
	
	private String token;
	
	public VerificationMailDetails(String username, String email, String token) {
		this.username = username;
		this.email = email;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUserame(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public VerificationMailDetails() {
	}

}
