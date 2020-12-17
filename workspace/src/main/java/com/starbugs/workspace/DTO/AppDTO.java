package com.starbugs.workspace.DTO;

import com.starbugs.workspace.Model.AppVersion;
import com.starbugs.workspace.Model.Application;

public class AppDTO {
	private Application app;
	private AppVersion version;
	
	public AppDTO() {
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public AppVersion getVersion() {
		return version;
	}

	public void setVersion(AppVersion version) {
		this.version = version;
	}

	public AppDTO(Application app, AppVersion version) {
		this.app = app;
		this.version = version;
	}
}
