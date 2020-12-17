package com.starbugs.Core.DTO;


public class ProjectRequest {

	private String name;
	private String domain;
	
	public ProjectRequest() {
	}

	public ProjectRequest(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}






	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	

}
