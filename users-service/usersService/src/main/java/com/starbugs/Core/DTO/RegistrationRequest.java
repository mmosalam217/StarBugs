package com.starbugs.Core.DTO;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.Company;

@Component
public class RegistrationRequest {
	
	private AppUser clientRootUser;
	private Company clientCompany;
	private UUID subscriptionId;
	
	public RegistrationRequest() {
	}

	public RegistrationRequest(AppUser clientRootUser, Company clientCompany, UUID subscriptionId) {
		this.clientRootUser = clientRootUser;
		this.clientCompany = clientCompany;
		this.subscriptionId = subscriptionId;
	}
	
	public AppUser getClientRootUser() {
		return clientRootUser;
	}

	public void setClientRootUser(AppUser clientRootUser) {
		this.clientRootUser = clientRootUser;
	}

	public Company getClientCompany() {
		return clientCompany;
	}

	public void setClientCompany(Company clientCompany) {
		this.clientCompany = clientCompany;
	}

	public UUID getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(UUID subscriptionId) {
		this.subscriptionId = subscriptionId;
	}



}
