package com.ecommerce.platform.service;

import com.ecommerce.platform.dto.AddToCartRequest;
import com.ecommerce.platform.dto.CartResponse;
import com.ecommerce.platform.dto.UpdateCartItemRequest;

public interface CartService {
	
	CartResponse addToCart(AddToCartRequest request);
	CartResponse getCart();
	CartResponse updateCartItem(UpdateCartItemRequest request);
	CartResponse removeCartItem(Long cartItemId);
	CartResponse clearCart();

}
