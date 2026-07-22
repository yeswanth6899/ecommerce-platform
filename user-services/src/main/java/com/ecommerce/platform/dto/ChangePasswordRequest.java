package com.ecommerce.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordRequest {
	
	@NotBlank(message = " Current password is required")
	private String currentPassword;
	
	@NotBlank(message = "New password is Required")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
			 message ="Password must contain at least 8 characters, one uppercase, one lowercase, one digit, and one special character")
	private String newPassword;
	
	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;

	public ChangePasswordRequest() {
		
	}

	public ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
		super();
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
