package com.starbugs.workspace.DTO;


import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.starbugs.workspace.Config.SimpleGrantedAuthorityDeserializer;




public class SharedUserDetails{

	private UUID id;
	private UUID clientId;
	private String username;
	@JsonDeserialize(using= SimpleGrantedAuthorityDeserializer.class)
	private Collection<GrantedAuthority> authorities = new ArrayList<>();


	public SharedUserDetails( String username, Collection<GrantedAuthority> authorities, UUID id, UUID clientId ) {
		this.id = id;
		this.username = username;
		this.clientId = clientId;
		this.authorities = authorities;
		
	}
	
	public SharedUserDetails() {}

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

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}


	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getUsername() {
		return this.username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public String toString() {
		return "{\"id\":" + getId() + ", \"clientId\":" + getClientId() + ", \"authorities\":" + authorities
				+ ", \"username\":" + getUsername() + "}";
	}



	


	

}
