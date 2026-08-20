package com.ecommerce.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddAddressRequest {
	
	@NotBlank(message = "Full name is required")
	private String fullName;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^\\d{10}$",
						message = "Phone number should have 10 digits")
	private String phoneNumber;
	
	@NotBlank(message = "Address line 1 is required")
	private String addressLine1;
	
	private String addressLine2;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message = "State is required")
	private String state;
	
	@NotBlank(message = "Postal code is required")
	@Pattern(
		    regexp = "^\\d{5}(-\\d{4})?$",
		    message = "Invalid postal code"
		)
	private String postalCode;
	
	@NotBlank(message = "Country is required")
	private String country;
	
	private Boolean defaultAddress = false;

	public AddAddressRequest() {
		
	}

	public AddAddressRequest(String fullName,String phoneNumber, String addressLine1, String addressLine2, String city,  String state,String postalCode, String country, Boolean defaultAddress) {
		
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.defaultAddress = defaultAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

}
