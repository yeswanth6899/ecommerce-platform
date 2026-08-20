package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.InventoryCreateRequest;
import com.ecommerce.platform.dto.InventoryResponse;
import com.ecommerce.platform.dto.InventoryUpdateRequest;
import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.Product;

@Component
public class InventoryMapper {
	
	public Inventory toEntity(InventoryCreateRequest request, Product product) {
		
		Inventory inventory = new Inventory();
		
		inventory.setAvailableStock(request.getAvailableStock());
		inventory.setReorderLevel(request.getReorderLevel());
		inventory.setReservedStock(0);
		inventory.setProduct(product);
		
		return inventory;
	}
	
	public InventoryResponse toResponse(Inventory inventory) {
		
		InventoryResponse response = new InventoryResponse();
		
		response.setId(inventory.getId());
		response.setAvailableStock(inventory.getAvailableStock());
		response.setReservedStock(inventory.getReservedStock());
		response.setReorderLevel(inventory.getReorderLevel());
		
		if (inventory.getProduct() != null) {
		    response.setProductId(inventory.getProduct().getId());
		    response.setProductName(inventory.getProduct().getName());
		}
		
		response.setCreatedAt(inventory.getCreatedAt());
		response.setUpdatedAt(inventory.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(Inventory inventory, InventoryUpdateRequest request) {

	    inventory.setAvailableStock(request.getAvailableStock());
	    inventory.setReorderLevel(request.getReorderLevel());
	    

	}

}
