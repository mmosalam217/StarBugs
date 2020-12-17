package com.starbugs.TicketService.Model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ticket_assignments")
public class TicketAssignment {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "assignee_id")
	@Type(type="uuid-char")
	private UUID assigneeID;
	
	@Column(name = "assignee_name")
	private String assigneeName;
	
	@Column(name = "assignee_email")
	private String assigneeEmail;
	
	@Column(name = "assignment_date")
	private Date assignmentDate;
	
	public TicketAssignment() {
	}


	public TicketAssignment(UUID id, UUID assigneeID, String assigneeName, String assigneeEmail, Date assignmentDate) {
		this.id = id;
		this.assigneeID = assigneeID;
		this.assigneeName = assigneeName;
		this.assigneeEmail = assigneeEmail;
		this.assignmentDate = assignmentDate;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public Date getAssignmentDate() {
		return assignmentDate;
	}


	public UUID getAssigneeID() {
		return assigneeID;
	}


	public void setAssigneeID(UUID assigneeID) {
		this.assigneeID = assigneeID;
	}


	public String getAssigneeName() {
		return assigneeName;
	}


	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}


	public String getAssigneeEmail() {
		return assigneeEmail;
	}


	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}




	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	
	

}
