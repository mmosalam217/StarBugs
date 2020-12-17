package com.starbugs.TicketService.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

@Entity
@Table(name= "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="ticket_gen")
	@TableGenerator(name="ticket_gen", table="ticket_gen", initialValue= 1111)
	@Column(name ="id", updatable = false, nullable = false)
	private Long id;
	

	
	@Column(name = "ticket_title", nullable = false)
	private String title;
	
	@Column(name = "issuer_id", nullable = false)
	@Type(type="uuid-char")
	private UUID issuerID;
	
	@Column(name = "issuer_name", nullable = false)
	private String issuerName;
	
	@Column(name = "client_id", nullable = false)
	@Type(type="uuid-char")
	private UUID clientID;
	
	@Column(name = "client_name", nullable = false)
	private String clientName;
	
	@Column(name = "project_id", nullable = false)
	@Type(type="uuid-char")
	private UUID projectID;
	
	@Column(name = "project_name", nullable = false)
	private String projectName;
	
	@Column(name = "app_id", nullable = false)
	@Type(type="uuid-char")
	private UUID app;
	
	@Column(name = "app_name", nullable = false)
	private String appName;
	
	@Column(name = "component_id", nullable = false)
	@Type(type="uuid-char")
	private UUID component;
	
	@Column(name = "component_name", nullable = false)
	private String componentName;
	
	@Column(name="created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private TicketAssignment assignee;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name ="ticket_id")
	private List<TicketAttachment> attachments = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name ="ticket_id")
	private List<TicketComment> comments = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name ="ticket_id")
	private List<TicketFollow> follows = new ArrayList<>();
	
	@Column(name="ticket_status", nullable = false)
	private String status;
	
	@Column(name = "severity", nullable = false)
	private String severity;
	
	@Column(name = "ticket_desc", nullable = false)
	private String desc;
	
	public Ticket() {
	}



	public Ticket(Long id, String title, UUID issuerID, String issuerName, UUID clientID, String clientName, UUID projectID,
			String projectName, UUID app, String appName, UUID component, String componentName, LocalDateTime createdAt,
			LocalDateTime updatedAt, TicketAssignment assignee, List<TicketAttachment> attachments,
			List<TicketComment> comments, List<TicketFollow> follows, String status, String severity, String desc) {
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
		this.assignee = assignee;
		this.attachments = attachments;
		this.comments = comments;
		this.follows = follows;
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



	public TicketAssignment getAssignee() {
		return assignee;
	}



	public void setAssignee(TicketAssignment assignee) {
		this.assignee = assignee;
	}



	public List<TicketAttachment> getAttachments() {
		return attachments;
	}



	public void setAttachments(List<TicketAttachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(TicketAttachment attachment) {
		this.attachments.add(attachment);
	}


	public List<TicketComment> getComments() {
		return comments;
	}



	public void setComments(List<TicketComment> comments) {
		this.comments = comments;
	}


	public void addComment(TicketComment comment) {
		this.comments.add(comment);
	}

	public void removeComment(TicketComment comment) {
		this.comments.remove(comment);
	}
	
	public List<TicketFollow> getFollows() {
		return follows;
	}



	public void setFollows(List<TicketFollow> follows) {
		this.follows = follows;
	}

	public void addFollow(TicketFollow follow) {
		this.follows.add(follow);
	}
	
	public void unFollow(TicketFollow follow) {
		this.follows.remove(follow);
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



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
}
