package com.starbugs.Core.DTO;



public class JWTResponse {

	private String token;
	private UserDto user;
	
	public JWTResponse(String token, UserDto user) {
		this.token = token;
		this.user =  user;
	}

	public JWTResponse() {
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}



}
