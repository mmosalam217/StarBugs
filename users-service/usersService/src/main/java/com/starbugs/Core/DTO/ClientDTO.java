package com.starbugs.Core.DTO;

import java.util.UUID;

public class ClientDTO {

	private UUID id;
	private String name;
	
	public ClientDTO() {}
	
	public ClientDTO(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
