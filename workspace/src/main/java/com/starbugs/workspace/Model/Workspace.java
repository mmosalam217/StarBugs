package com.starbugs.workspace.Model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="client_workspaces")
public class Workspace {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name="client_id", nullable= false)
	@Type(type="uuid-char")
	private UUID clientId;

	@Column(name="name")
	private String name;
	
	public Workspace() {
		
	}
	
	public Workspace(UUID id, UUID clientId, String name) {
		this.id = id;
		this.clientId = clientId;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getClientId() {
		return clientId;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}
	
	
	
}
