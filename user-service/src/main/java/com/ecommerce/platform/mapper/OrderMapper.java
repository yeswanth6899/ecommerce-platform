package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.OrderItemResponse;
import com.ecommerce.platform.dto.OrderResponse;
import com.ecommerce.platform.dto.ShippingAddressResponse;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.OrderItem;

@Component
public class OrderMapper {

	public OrderResponse toResponse(Order order) {
		
		OrderResponse response = new OrderResponse();
		
		response.setOrderId(order.getId());
		response.setOrderNumber(order.getOrderNumber());
		response.setOrderedAt(order.getCreatedAt());
		
		response.setOrderStatus(order.getOrderStatus());
		response.setPaymentStatus(order.getPaymentStatus());
		response.setPaymentMethod(order.getPaymentMethod());
		
		response.setSubtotal(order.getSubtotal());
		response.setTaxAmount(order.getTaxAmount());
		response.setShippingCost(order.getShippingCost());
		response.setDiscountAmount(order.getDiscountAmount());
		response.setTotalAmount(order.getTotalAmount());
		response.setTotalQuantity(order.getTotalQuantity());
		
		response.setShippingAddress(mapShippingAddress(order));
		
		response.setItems(order.getOrderItems()
								.stream()
								.map(this :: mapOrderItem)
								.toList());
		
		return response;
		
	}
	
	private OrderItemResponse mapOrderItem(OrderItem orderItem) {
		
		OrderItemResponse response  = new OrderItemResponse();
		
		response.setProductId(orderItem.getProduct().getId());
		response.setProductName(orderItem.getProductName());
		response.setProductImageUrl(orderItem.getProductImageUrl());
		response.setUnitPrice(orderItem.getUnitPrice());
		response.setQuantity(orderItem.getQuantity());
		response.setSubtotal(orderItem.getSubtotal());
		
		return response;
	}
	
	private ShippingAddressResponse mapShippingAddress(Order order) {
		
		ShippingAddressResponse response = new ShippingAddressResponse();
		
		response.setFullName(order.getShippingFullName());
		response.setPhoneNumber(order.getShippingPhoneNumber());
		response.setAddressLine1(order.getShippingAddressLine1());
		response.setAddressLine2(order.getShippingAddressLine2());
		response.setCity(order.getShippingCity());
		response.setState(order.getShippingState());
		response.setPostalCode(order.getShippingPostalCode());
		response.setCountry(order.getShippingCountry());
		
		return response;
	}
	
}
