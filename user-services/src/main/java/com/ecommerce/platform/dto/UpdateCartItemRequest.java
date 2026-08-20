package com.ecommerce.platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class UpdateCartItemRequest {
	
	@NotNull(message = "cart item id is required")
	private Long cartItemId;
	
	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity cannot be negative")
	private Integer quantity;

	public UpdateCartItemRequest() {
		
	}

	public UpdateCartItemRequest(Long cartItemId, Integer quantity) {
		
		this.cartItemId = cartItemId;
		this.quantity = quantity;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
