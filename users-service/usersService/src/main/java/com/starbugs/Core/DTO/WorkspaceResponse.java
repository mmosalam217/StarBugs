package com.starbugs.Core.DTO;

import org.springframework.http.HttpStatus;

public class WorkspaceResponse {

	private int status;
	private String message;
	private HttpStatus statusText;
	private StarBugsWorkspace workspace;
	public WorkspaceResponse(int status, String message, HttpStatus statusText, StarBugsWorkspace workspace) {
		super();
		this.status = status;
		this.message = message;
		this.statusText = statusText;
		this.workspace = workspace;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatusText() {
		return statusText;
	}
	public void setStatusText(HttpStatus statusText) {
		this.statusText = statusText;
	}
	public StarBugsWorkspace getWorkspace() {
		return workspace;
	}
	public void setWorkspace(StarBugsWorkspace workspace) {
		this.workspace = workspace;
	}
	
	
}
