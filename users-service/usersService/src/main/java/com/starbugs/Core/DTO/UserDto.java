package com.starbugs.Core.DTO;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;


public class UserDto {

	private UUID id;
	private UUID clientId;
	private String firstName;
	private String lastName;
	private String username;
	private String displayName;
	private Collection<GrantedAuthority> authorities;
	
	public UserDto(UUID id, String username, String firstName, String lastName, String displayName, UUID clientId, Collection<GrantedAuthority> authorities) {
		this.id = id;
		this.displayName = displayName;
		this.clientId = clientId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.authorities = authorities;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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



}
