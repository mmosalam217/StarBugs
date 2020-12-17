package com.starbugs.Core.DTO;

import java.util.List;

import com.starbugs.Core.Model.Project;

public class AddProjectResponse {

	private List<Project> projects;
	private String message;
	
	public AddProjectResponse(List<Project> projects, String message) {
		this.projects = projects;
		this.message = message;
	}

	public AddProjectResponse() {
	}

	public List<Project> getProjects() {
		return projects;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
