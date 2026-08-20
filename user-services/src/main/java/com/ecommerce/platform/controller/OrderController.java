package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.OrderResponse;
import com.ecommerce.platform.dto.PlaceOrderRequest;
import com.ecommerce.platform.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request){
		
		OrderResponse response = orderService.placeOrder(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getMyOrders(){
		
		return ResponseEntity.ok(orderService.getMyOrders());
		
	}
	
	@GetMapping("/{orderNumber}")
	public ResponseEntity<OrderResponse> getOrderByOrderNumber(@PathVariable String orderNumber){
		
		return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
	}

}
