package com.ecommerce.platform.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.AddToCartRequest;
import com.ecommerce.platform.dto.CartResponse;
import com.ecommerce.platform.dto.UpdateCartItemRequest;
import com.ecommerce.platform.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	
	private final CartService cartService;

	public CartController(CartService cartService) {
		super();
		this.cartService = cartService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request){
		
		CartResponse response = cartService.addToCart(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
		
	}
	
	@GetMapping
	public ResponseEntity<CartResponse> getCart(){
		
		return ResponseEntity.ok(cartService.getCart());
	}
	
	@PutMapping("/item")
	public ResponseEntity<CartResponse> updateCartItem(@Valid @RequestBody UpdateCartItemRequest request){
		
		return ResponseEntity.ok(cartService.updateCartItem(request));
	}
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<CartResponse> removeCartItem(@PathVariable Long cartItemId){
		
		return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<CartResponse> clearCart(){
		
		return ResponseEntity.ok(cartService.clearCart());
		
	}

}
