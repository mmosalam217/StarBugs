package com.starbugs.Core.Model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "verificationTokens")
public class VerificationToken {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(length=3000)
	private String token;
	
	private UUID user;
	
	private boolean expired;
	
	public VerificationToken() {
	}
	
	public boolean isExpired() {
		return expired;
	}



	public void setExpired(boolean expired) {
		this.expired = expired;
	}



	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public UUID getUser() {
		return user;
	}



	public void setUser(UUID user) {
		this.user = user;
	}

}
