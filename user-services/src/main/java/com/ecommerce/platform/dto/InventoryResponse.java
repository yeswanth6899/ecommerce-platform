package com.ecommerce.platform.dto;

import java.time.LocalDateTime;

public class InventoryResponse {
	
	private Long id;
	private Integer availableStock;
	private Integer reservedStock;
	private Integer reorderLevel;
	private Long productId;
	private String productName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	public InventoryResponse() {
		
	}


	public InventoryResponse(Long id, Integer availableStock, Integer reservedStock, Integer reorderLevel,
			Long productId, String productName, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.availableStock = availableStock;
		this.reservedStock = reservedStock;
		this.reorderLevel = reorderLevel;
		this.productId = productId;
		this.productName = productName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
