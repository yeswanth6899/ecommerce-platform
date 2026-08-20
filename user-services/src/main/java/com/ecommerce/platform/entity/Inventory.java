package com.ecommerce.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message  = "Available Stock is required")
	@PositiveOrZero(message = "Available Stock cannot be negeative")
	private Integer availableStock;
	
	@NotNull(message  = "Reserved Stock is required")
	@PositiveOrZero(message = "Reserved Stock cannot be negeative")
	private Integer reservedStock;
	
	@NotNull(message  = "Reorder level is required")
	@PositiveOrZero(message = "Reorder level cannot be negeative")
	private Integer reorderLevel;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id",  nullable = false, unique = true)
	private Product product;
	
	public Inventory() {
		
	}

	public Inventory(Integer availableStock, Integer reservedStock, Integer reorderLevel, Product product) {
		
		this.availableStock = availableStock;
		this.reservedStock = reservedStock;
		this.reorderLevel = reorderLevel;
		this.product = product;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getAvailableStock() {
		return availableStock;
	}


	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}


	public Integer getReservedStock() {
		return reservedStock;
	}


	public void setReservedStock(Integer reservedStock) {
		this.reservedStock = reservedStock;
	}


	public Integer getReorderLevel() {
		return reorderLevel;
	}


	public void setReorderLevel(Integer reorderLevel) {
		this.reorderLevel = reorderLevel;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
}
