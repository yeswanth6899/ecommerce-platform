package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.ShipmentResponse;
import com.ecommerce.platform.entity.Shipment;

@Component
public class ShipmentMapper {
	
	public ShipmentResponse toResponse(Shipment shipment) {
		
		ShipmentResponse response = new ShipmentResponse();
		
		response.setTrackingNumber(shipment.getTrackingNumber());
		response.setOrderNumber(shipment.getOrder().getOrderNumber());
		response.setCarrier(shipment.getCarrier());
		response.setShippingStatus(shipment.getShippingStatus());
		response.setEstimatedDeliveryDate(shipment.getEstimatedDeliveryDate());
		response.setShippedAt(shipment.getShippedAt());
		response.setDeliveredAt(shipment.getDeliveredAt());
		
		return response;
	}

}
