package com.starbugs.workspace.DTO;

import java.util.List;


public class WorkspaceDTO {
	
	private ClientDTO client;
	private List<ProjectDTO> projects;
	
	public WorkspaceDTO() {
		
	}
	public WorkspaceDTO(ClientDTO client, List<ProjectDTO> projects) {
		this.client = client;
		this.projects = projects;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}


	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}
	

}
