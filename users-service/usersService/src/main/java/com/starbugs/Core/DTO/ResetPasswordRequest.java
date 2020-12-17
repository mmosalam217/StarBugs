package com.starbugs.Core.DTO;

public class ResetPasswordRequest {

	private String newPassword;
	private String newPasswordConfirmation;
	
	public ResetPasswordRequest() {
		
	}
	
	public ResetPasswordRequest(String newPassword, String newPasswordConfirmation) {


		this.newPassword = newPassword;
		this.newPasswordConfirmation = newPasswordConfirmation;
	}



	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirmation() {
		return newPasswordConfirmation;
	}

	public void setNewPasswordConfirmation(String newPasswordConfirmation) {
		this.newPasswordConfirmation = newPasswordConfirmation;
	}



}
