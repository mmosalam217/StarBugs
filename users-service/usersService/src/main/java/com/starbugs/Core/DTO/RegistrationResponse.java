package com.starbugs.Core.DTO;

import com.starbugs.Core.Model.StarBugsClient;

public class RegistrationResponse {

	private StarBugsClient client;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RegistrationResponse(StarBugsClient client, String message) {
		this.client = client;
		this.message = message;
		
	}

	public RegistrationResponse() {
	}
	
	public StarBugsClient getClient() {
		return client;
	}

	public void setClient(StarBugsClient client) {
		this.client = client;
	}
	

}
