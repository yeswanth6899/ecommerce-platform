package com.ecommerce.platform.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.CartItemResponse;
import com.ecommerce.platform.dto.CartResponse;
import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.CartItem;
import com.ecommerce.platform.entity.Product;

@Component
public class CartMapper {
	
	public CartItemResponse toCartItemResponse(CartItem cartItem) {
		
		Product product  = cartItem.getProduct();
		
		CartItemResponse response = new CartItemResponse();
		
		response.setCartItemId(cartItem.getId());
		response.setProductId(product.getId());
		response.setProductName(product.getName());
		response.setImageUrl(product.getImageUrl());
		response.setUnitPrice(product.getPrice());
		response.setQuantity(cartItem.getQuantity());
		response.setSubtotal(product.getPrice()
										.multiply(BigDecimal.valueOf(cartItem.getQuantity()))
							);
		
		return response;
	}
	
	public CartResponse toCartResponse(Cart cart) {
		
		List<CartItem> cartItems = cart.getCartItems();
		
		List<CartItemResponse> cartItemResponses = cartItems
														.stream()
														.map(this :: toCartItemResponse)
														.toList();
		
		int totalItems = cartItems
								.stream()
								.mapToInt(CartItem :: getQuantity)
								.sum();
		
		BigDecimal totalAmount = cartItemResponses.stream()
									.map(CartItemResponse :: getSubtotal)
									.reduce(BigDecimal.ZERO, BigDecimal :: add);
		
		CartResponse response = new CartResponse();
		
		response.setCartItems(cartItemResponses);
		response.setTotalItems(totalItems);
		response.setTotalAmount(totalAmount);
		
		return response;
		
		
	}

}
