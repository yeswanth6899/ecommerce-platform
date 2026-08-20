package com.ecommerce.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserProfileRequest {
	
	@NotBlank(message="First name is Required")
	@Size(min =2, max=50)
	@Pattern(
		    regexp = "^[A-Za-z ]+$",
		    message = "First name should contain only alphabets")
	private String firstName;
	@NotBlank(message = "Last name is Required")
	@Size(min =2, max=50)
	@Pattern(
		    regexp = "^[A-Za-z]+$",
		    message = "First name should contain only alphabets")
	private String lastName;
	
	@NotBlank(message = "Phone number is Required")
	@Pattern(regexp = "^[0-9]{10}$",
			 message = "Phone number must be exactly 10 digits")
	private String phoneNumber;
	
	
	public UserProfileRequest() {
		
	}


	public UserProfileRequest( String firstName,String lastName, String phoneNumber) {
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
