package com.starbugs.Core.DTO;

import java.util.List;
import java.util.UUID;

import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Model.Project;

public class StarBugsWorkspace {

	private UUID clientId;
	private Company company;
	private List<Project> projects;
	public StarBugsWorkspace(UUID clientId, Company company, List<Project> projects) {
		super();
		this.clientId = clientId;
		this.company = company;
		this.projects = projects;
	}
	public UUID getClientId() {
		return clientId;
	}
	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
}
