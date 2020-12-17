package com.starbugs.Core.DTO;

import com.starbugs.Core.Model.Project;

public class UpdateProjectResponse {
	
	private String message;
	private Project updatedProject;
	
	public UpdateProjectResponse() {
	}

	public UpdateProjectResponse(String message, Project updatedProject) {
		this.message = message;
		this.updatedProject = updatedProject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Project getUpdatedProject() {
		return updatedProject;
	}

	public void setUpdatedProject(Project updatedProject) {
		this.updatedProject = updatedProject;
	}

}
