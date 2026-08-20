package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.ShipmentResponse;
import com.ecommerce.platform.service.ShipmentService;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
	
	private final ShipmentService shipmentService;

	public ShipmentController(ShipmentService shipmentService) {
		
		this.shipmentService = shipmentService;
	}
	
	@GetMapping("/{trackingNumber}")
	public ResponseEntity<ShipmentResponse> getShipmentByTrackingNumber(@PathVariable String trackingNumber){
		
		ShipmentResponse response = shipmentService.getShipmentByTrackingNumber(trackingNumber);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/order/{orderNumber}")
	public ResponseEntity<List<ShipmentResponse>> getShipmentByOrderNumber(@PathVariable String orderNumber){
		
		List<ShipmentResponse> response = shipmentService.getShipmentByOrderNumber(orderNumber);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<ShipmentResponse>> getMyShipments(){
		
		List<ShipmentResponse> response = shipmentService.getMyShipments();
		
		return ResponseEntity.ok(response);
	}

}
