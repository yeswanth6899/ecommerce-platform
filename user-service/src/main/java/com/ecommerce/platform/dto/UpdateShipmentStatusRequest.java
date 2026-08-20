package com.ecommerce.platform.dto;

import com.ecommerce.platform.entity.ShippingStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateShipmentStatusRequest {
	
	@NotNull(message = "Shipping status is required")
	private ShippingStatus shippingStatus;

	public UpdateShipmentStatusRequest() {
		
	}

	public UpdateShipmentStatusRequest(ShippingStatus shippingStatus) {
		
		this.shippingStatus = shippingStatus;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

}
