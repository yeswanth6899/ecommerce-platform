package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.ShipmentResponse;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.ShippingStatus;

public interface ShipmentService {
	
	void createShipment(Order order);
	
	ShipmentResponse getShipmentByTrackingNumber(String trackingNumber);
	
	List<ShipmentResponse> getShipmentByOrderNumber(String orderNumber);
	
	List<ShipmentResponse> getMyShipments();
	
	ShipmentResponse adminUpdateShippingStatus(String trackingNumber, ShippingStatus newStatus);
	
	

}
