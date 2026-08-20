package com.ecommerce.platform.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartResponse {

	private List<CartItemResponse> cartItems = new ArrayList<>();

    private Integer totalItems;

    private BigDecimal totalAmount;

	public CartResponse() {
		
	}

	public CartResponse(List<CartItemResponse> cartItems, Integer totalItems, BigDecimal totalAmount) {
		
		this.cartItems = cartItems;
		this.totalItems = totalItems;
		this.totalAmount = totalAmount;
	}

	public List<CartItemResponse> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemResponse> cartItems) {
		this.cartItems = cartItems;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
