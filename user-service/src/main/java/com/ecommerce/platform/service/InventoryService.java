package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.InventoryCreateRequest;
import com.ecommerce.platform.dto.InventoryResponse;
import com.ecommerce.platform.dto.InventoryUpdateRequest;
import com.ecommerce.platform.entity.Order;


public interface InventoryService {
	
	//Admin operations
	
	InventoryResponse createInventory(InventoryCreateRequest request);
	
	InventoryResponse getInventoryById(Long inventoryId);
	
	List<InventoryResponse> getAllInventories();
	
	InventoryResponse updateInventory(Long inventoryId, InventoryUpdateRequest request);
	
	
	//Business Operations
	
	void reserveStock(Order order);
	
	void confirmReservation(Order order);
	
	void releaseReservation(Order order);

}
