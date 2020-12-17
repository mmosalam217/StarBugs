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
@Table(name = "ticket_comments")
public class TicketComment {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "commentator_id")
	@Type(type="uuid-char")
	private UUID commentatorID;
	
	@Column(name = "commentator_name")
	private String commentatorName;
	
	@Column(name = "commentator_email")
	private String commentatorEmail;
	
	@Column(name = "comment_content")
	private String commentContent;
	
	@Column(name = "comment_date")
	private Date commentDate;
	

	
	public TicketComment() {
	}

	public TicketComment(UUID id, UUID commentatorID, String commentatorName, String commentatorEmail, String commentContent, Date commentDate) {
		this.id = id;
		this.commentatorID = commentatorID;
		this.commentatorName = commentatorName;
		this.commentatorEmail = commentatorEmail;
		this.commentContent = commentContent;
		this.commentDate = commentDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCommentatorID() {
		return commentatorID;
	}

	public void setCommentatorID(UUID commentatorID) {
		this.commentatorID = commentatorID;
	}

	public String getCommentatorName() {
		return commentatorName;
	}

	public void setCommentatorName(String commentatorName) {
		this.commentatorName = commentatorName;
	}

	public String getCommentatorEmail() {
		return commentatorEmail;
	}

	public void setCommentatorEmail(String commentatorEmail) {
		this.commentatorEmail = commentatorEmail;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	
	
}
