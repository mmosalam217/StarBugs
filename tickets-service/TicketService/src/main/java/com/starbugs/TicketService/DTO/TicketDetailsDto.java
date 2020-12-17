package com.starbugs.TicketService.DTO;

import java.time.LocalDateTime;

import java.util.UUID;


public class TicketDetailsDto {
	private Long id;
	private String title;
	private UUID issuerID;
	private String issuerName;
	private UUID clientID;
	private String clientName;
	private UUID projectID;
	private String projectName;
	private UUID app;
	private String appName;
	private UUID component;
	private String componentName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UUID assigneeId;
	private String assigneeName;
	private String status;
	private String severity;
	private String desc;
	public TicketDetailsDto(Long id, String title, UUID issuerID, String issuerName, UUID clientID, String clientName,
			UUID projectID, String projectName, UUID app, String appName, UUID component, String componentName,
			LocalDateTime createdAt, LocalDateTime updatedAt, UUID assigneeId, String assigneeName, String status,
			String severity, String desc) {
		super();
		this.id = id;
		this.title = title;
		this.issuerID = issuerID;
		this.issuerName = issuerName;
		this.clientID = clientID;
		this.clientName = clientName;
		this.projectID = projectID;
		this.projectName = projectName;
		this.app = app;
		this.appName = appName;
		this.component = component;
		this.componentName = componentName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.assigneeId = assigneeId;
		this.assigneeName = assigneeName;
		this.status = status;
		this.severity = severity;
		this.desc = desc;
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
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public UUID getClientID() {
		return clientID;
	}
	public void setClientID(UUID clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public UUID getProjectID() {
		return projectID;
	}
	public void setProjectID(UUID projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public UUID getApp() {
		return app;
	}
	public void setApp(UUID app) {
		this.app = app;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public UUID getComponent() {
		return component;
	}
	public void setComponent(UUID component) {
		this.component = component;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
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
	public UUID getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(UUID assigneeId) {
		this.assigneeId = assigneeId;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
