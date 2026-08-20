package com.ecommerce.platform.entity;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "inventory_reservations")
public class InventoryReservation  extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_id", nullable = false)
	private Inventory inventory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@NotNull(message = "Reserved quantity is required")
	@Positive(message = "Reserved quantity must be grater than zero")
	private Integer quantity;
	
	@NotNull(message = "Reservation status is required")
	@Enumerated(EnumType.STRING)
	private InventoryReservationStatus inventoryReservationStatus;
	
	
	public InventoryReservation() {
		
	}


	public InventoryReservation(Inventory inventory, Order order, Integer quantity,
			InventoryReservationStatus inventoryReservationStatus) {
		
		this.inventory = inventory;
		this.order = order;
		this.quantity = quantity;
		this.inventoryReservationStatus = inventoryReservationStatus;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public InventoryReservationStatus getInventoryReservationStatus() {
		return inventoryReservationStatus;
	}


	public void setInventoryReservationStatus(InventoryReservationStatus inventoryReservationStatus) {
		this.inventoryReservationStatus = inventoryReservationStatus;
	}
}
