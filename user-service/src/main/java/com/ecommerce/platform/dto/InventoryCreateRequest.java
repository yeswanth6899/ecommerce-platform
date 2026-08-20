package com.ecommerce.platform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class InventoryCreateRequest {
	
	@NotNull(message = "Available Stock is required")
	@PositiveOrZero(message = "Available Stock cannot be negative")
	private Integer availableStock;
	
	@NotNull(message = "Reorder level is required")
	@PositiveOrZero(message = "Reorder level cannot be negative")
	private Integer reorderLevel;
	
	@NotNull(message = "Product id is required")
	@Positive(message = "Product ID must be greater than zero")
	private Long productId;
	
	public InventoryCreateRequest() {
		
	}

	public InventoryCreateRequest(Integer availableStock, Integer reorderLevel, Long productId) {
		this.availableStock = availableStock;
		this.reorderLevel = reorderLevel;
		this.productId = productId;
	}

	public Integer getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}

	public Integer getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(Integer reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
