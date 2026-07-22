package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.ProductRequest;
import com.ecommerce.platform.dto.ProductResponse;
import com.ecommerce.platform.entity.Category;
import com.ecommerce.platform.entity.Product;

@Component
public class ProductMapper {
	
	public Product toEntity(ProductRequest request, Category category) {
		
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setImageUrl(request.getImageUrl());
		product.setCategory(category);
		return product;
		
	}

	
	public ProductResponse toResponse(Product product) {
		
		ProductResponse response = new ProductResponse();
		
		response.setId(product.getId());
		response.setName(product.getName());
		response.setPrice(product.getPrice());
		response.setDescription(product.getDescription());
		response.setStockQuantity(product.getStockQuantity());
		response.setImageUrl(product.getImageUrl());
		response.setCategoryId(product.getCategory().getId());
		response.setCategoryName(product.getCategory().getName());
		
		return response;
		
	}
	
	public void updateEntity(Product product, ProductRequest request, Category category) {
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setImageUrl(request.getImageUrl());
		product.setCategory(category);
		
	}
}
