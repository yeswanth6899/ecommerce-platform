package com.ecommerce.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.ShipmentResponse;
import com.ecommerce.platform.dto.UpdateShipmentStatusRequest;
import com.ecommerce.platform.service.ShipmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private final ShipmentService shipmentService;
	
	
	public AdminController(ShipmentService shipmentService) {
		
		this.shipmentService = shipmentService;
	}


	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('ADMIN')")
	public String dashboard() {
		
		return "Welcome Admin!";
	}
	
	@PatchMapping("/shipments/{trackingNumber}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ShipmentResponse> updateShipmentStatus(@PathVariable String trackingNumber,
																@Valid @RequestBody UpdateShipmentStatusRequest request){
		
		ShipmentResponse response = shipmentService.adminUpdateShippingStatus(trackingNumber, request.getShippingStatus());
		
		return ResponseEntity.ok(response);
	}

}
