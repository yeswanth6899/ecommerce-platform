package com.ecommerce.platform.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String trackingNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ShippingCarrier carrier;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ShippingStatus shippingStatus;
	
	private LocalDate estimatedDeliveryDate;
	private LocalDateTime shippedAt;
	private LocalDateTime deliveredAt;
	
	
	public Shipment() {
		
	}


	public Shipment(String trackingNumber, Order order, ShippingCarrier carrier, ShippingStatus shippingStatus,
			LocalDate estimatedDeliveryDate, LocalDateTime shippedAt, LocalDateTime deliveredAt) {
		
		this.trackingNumber = trackingNumber;
		this.order = order;
		this.carrier = carrier;
		this.shippingStatus = shippingStatus;
		this.estimatedDeliveryDate = estimatedDeliveryDate;
		this.shippedAt = shippedAt;
		this.deliveredAt = deliveredAt;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTrackingNumber() {
		return trackingNumber;
	}


	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
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
