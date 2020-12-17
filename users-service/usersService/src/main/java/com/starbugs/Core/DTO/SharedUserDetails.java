package com.starbugs.Core.DTO;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.starbugs.Core.Config.SimpleGrantedAuthorityDeserializer;

public class SharedUserDetails {
	private UUID id;
	private UUID clientId;
	private String username;
	
	@JsonDeserialize(using= SimpleGrantedAuthorityDeserializer.class)
	private Collection<GrantedAuthority> authorities;
	
	public SharedUserDetails(UUID id, UUID clientId, String username, Collection<GrantedAuthority> authorities) {
		this.id = id;
		this.clientId = clientId;
		this.username = username;
		this.authorities = authorities;
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

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ", \"clientId\":" + clientId + ", \"username\":" + username + ", \"authorities\":"
				+ authorities + "}";
	}
	
	
	
	
}
