package com.starbugs.workspace.Model;

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
@Table(name="app_versions")
public class AppVersion {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	private String name;
	
	private Date date;
	
	@Column(name="ver_status")
	private String status;
	
	public AppVersion() {
	}
	
	public AppVersion(UUID id, String name, Date date, String status) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.status = status;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}







	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}

}
