package com.starbugs.workspace.DTO;

import java.util.List;
import java.util.UUID;

import com.starbugs.workspace.Model.Application;

public class ProjectDTO {
	
	private UUID id;
	private String name;
	private List<Application> applications;
	
	public ProjectDTO() {}
	
	public ProjectDTO(UUID id, String name, List<Application> applications) {
		this.id = id;
		this.name = name;
		this.applications = applications;
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

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	

}
