package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.InventoryCreateRequest;
import com.ecommerce.platform.dto.InventoryResponse;
import com.ecommerce.platform.dto.InventoryUpdateRequest;
import com.ecommerce.platform.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {
	
	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {

		this.inventoryService = inventoryService;
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryCreateRequest request){
		
		InventoryResponse response = inventoryService.createInventory(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
	}
	
	@GetMapping("/{inventoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long inventoryId){
		
		InventoryResponse response = inventoryService.getInventoryById(inventoryId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InventoryResponse>> getAllInventories(){
		
		List<InventoryResponse> response = inventoryService.getAllInventories();
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/{inventoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Long inventoryId,@Valid @RequestBody InventoryUpdateRequest request){
		
		InventoryResponse response = inventoryService.updateInventory(inventoryId, request);
		
		return ResponseEntity.ok(response);
	}

}
