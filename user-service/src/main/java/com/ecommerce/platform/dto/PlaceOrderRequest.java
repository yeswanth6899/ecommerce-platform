package com.ecommerce.platform.dto;

import com.ecommerce.platform.entity.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public class PlaceOrderRequest {
	
	@NotNull(message  = "Shipping address id is required")
	private Long shippingAddressId;
	
	@NotNull(message = "Payment method is required")
	private PaymentMethod paymentMethod;
	
	
	public PlaceOrderRequest() {
		
	}


	public PlaceOrderRequest(Long shippingAddressId, PaymentMethod paymentMethod) {
		
		this.shippingAddressId = shippingAddressId;
		this.paymentMethod = paymentMethod;
	}


	public Long getShippingAddressId() {
		return shippingAddressId;
	}


	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}


	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
