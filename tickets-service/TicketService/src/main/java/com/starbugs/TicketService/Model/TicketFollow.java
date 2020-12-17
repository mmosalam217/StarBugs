package com.starbugs.TicketService.Model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ticket_follows")
public class TicketFollow {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "follower_id")
	@Type(type="uuid-char")
	private UUID followerID;
	
	
	@Column(name = "follower_name")
	private String followerName;
	
	@Column(name = "follower_email")
	private  String followerEmail;
	
	
	public TicketFollow() {
	}


	public TicketFollow(UUID id, UUID followerID, String followerName, String followerEmail) {
		this.id = id;
		this.followerID = followerID;
		this.followerName = followerName;
		this.followerEmail = followerEmail;

	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}



	public UUID getFollowerID() {
		return followerID;
	}


	public void setFollowerID(UUID followerID) {
		this.followerID = followerID;
	}


	public String getFollowerName() {
		return followerName;
	}


	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}


	public String getFollowerEmail() {
		return followerEmail;
	}


	public void setFollowerEmail(String followerEmail) {
		this.followerEmail = followerEmail;
	}
	

}
