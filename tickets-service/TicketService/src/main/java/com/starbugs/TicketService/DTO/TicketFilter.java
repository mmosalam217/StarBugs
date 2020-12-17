package com.starbugs.TicketService.DTO;

import java.time.LocalDateTime;

import java.util.UUID;






public class TicketFilter {
	
	private Long id;
	private String title;
	private UUID issuerID;
	private UUID clientID;
	private UUID projectID;
	private UUID app;
	private UUID component;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String status;
	private String severity;
	
	public TicketFilter(Long id, String title, UUID issuerID, UUID clientID,
			UUID projectID, UUID app, UUID component, LocalDateTime createdAt, LocalDateTime updatedAt, String status, String severity) {
		this.id = id;
		this.title = title;
		this.issuerID = issuerID;
		this.clientID = clientID;
		this.projectID = projectID;
		this.app = app;
		this.component = component;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.severity = severity;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public UUID getIssuerID() {
		return issuerID;
	}
	public void setIssuerID(UUID issuerID) {
		this.issuerID = issuerID;
	}

	public UUID getClientID() {
		return clientID;
	}
	public void setClientID(UUID clientID) {
		this.clientID = clientID;
	}

	public UUID getProjectID() {
		return projectID;
	}
	public void setProjectID(UUID projectID) {
		this.projectID = projectID;
	}

	public UUID getApp() {
		return app;
	}
	public void setApp(UUID app) {
		this.app = app;
	}
	
	public UUID getComponent() {
		return component;
	}
	public void setComponent(UUID component) {
		this.component = component;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	
	
}
