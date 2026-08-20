package com.ecommerce.platform.dto;

import jakarta.validation.constraints.NotBlank;

public class PaymentRequest {
	
	@NotBlank(message = "Order number is required")
	private String orderNumber;

	public PaymentRequest() {
		
	}

	public PaymentRequest(String orderNumber) {
		
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
