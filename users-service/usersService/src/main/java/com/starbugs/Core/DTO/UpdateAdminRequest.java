package com.starbugs.Core.DTO;

import java.util.UUID;

public class UpdateAdminRequest {

	private UUID newAdmin;
	
	public UpdateAdminRequest(UUID newAdmin) {
		super();
		this.newAdmin = newAdmin;
	}

	public UUID getNewAdmin() {
		return newAdmin;
	}

	public void setNewAdmin(UUID newAdmin) {
		this.newAdmin = newAdmin;
	}

	public UpdateAdminRequest() {
	}

}
