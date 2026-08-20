package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.OrderResponse;
import com.ecommerce.platform.dto.PlaceOrderRequest;

public interface OrderService {
	
	OrderResponse placeOrder(PlaceOrderRequest request);
	
	List<OrderResponse> getMyOrders();
	
	OrderResponse getOrderByOrderNumber(String orderNumber);

}
