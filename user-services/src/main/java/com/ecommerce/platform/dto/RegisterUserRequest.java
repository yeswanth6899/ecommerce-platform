package com.ecommerce.platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {
	
	@NotBlank(message = "First Name is Required")
	@Size(min =2, max=50)
	@Pattern(
		    regexp = "^[A-Za-z ]+$",
		    message = "First name should contain only alphabets")
	private String firstName;
	
	@NotBlank(message = "Last Name is Required")
	@Size(min =2, max=50)
	@Pattern(
		    regexp = "^[A-Za-z]+$",
		    message = "First name should contain only alphabets")
	private String lastName;
	
	@NotBlank(message = "Email is Required")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password is Required")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
			 message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;
	
	@NotBlank(message = "Phone number is Required")
	@Pattern(regexp = "^[0-9]{10}$",
			 message = "Phone number must be exactly 10 digits")
	private String phoneNumber;
	
	public RegisterUserRequest() {
		
	}


	


	public RegisterUserRequest(String firstName, String lastName,String email, String password,String phoneNumber) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
