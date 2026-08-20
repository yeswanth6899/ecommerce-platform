package com.ecommerce.platform.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ecommerce.platform.entity.ShippingCarrier;
import com.ecommerce.platform.entity.ShippingStatus;

public class ShipmentResponse {
	
	private String trackingNumber;
	private String orderNumber;
	private ShippingCarrier carrier;
	private ShippingStatus shippingStatus;
	private LocalDate estimatedDeliveryDate;
	private LocalDateTime shippedAt;
	private LocalDateTime deliveredAt;
	
	
	public ShipmentResponse() {
		
	}


	public ShipmentResponse(String trackingNumber, String orderNumber, ShippingCarrier carrier,
			ShippingStatus shippingStatus, LocalDate estimatedDeliveryDate, LocalDateTime shippedAt,
			LocalDateTime deliveredAt) {
		
		this.trackingNumber = trackingNumber;
		this.orderNumber = orderNumber;
		this.carrier = carrier;
		this.shippingStatus = shippingStatus;
		this.estimatedDeliveryDate = estimatedDeliveryDate;
		this.shippedAt = shippedAt;
		this.deliveredAt = deliveredAt;
	}


	public String getTrackingNumber() {
		return trackingNumber;
	}


	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public ShippingCarrier getCarrier() {
		return carrier;
	}


	public void setCarrier(ShippingCarrier carrier) {
		this.carrier = carrier;
	}


	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}


	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}


	public LocalDate getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}


	public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}


	public LocalDateTime getShippedAt() {
		return shippedAt;
	}


	public void setShippedAt(LocalDateTime shippedAt) {
		this.shippedAt = shippedAt;
	}


	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}


	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

}
