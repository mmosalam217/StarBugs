package com.starbugs.Core.DTO;

import com.starbugs.Core.Model.Application;
import com.starbugs.Core.Model.Version;

public class AddAppRequest {
	
	private Application app;
	private Version version;
	
	public AddAppRequest() {
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public AddAppRequest(Application app, Version version) {
		this.app = app;
		this.version = version;
	}

}
