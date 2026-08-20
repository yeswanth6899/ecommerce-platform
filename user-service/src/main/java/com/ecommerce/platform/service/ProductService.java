package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.ProductRequest;
import com.ecommerce.platform.dto.ProductResponse;

public interface ProductService {

	
	ProductResponse createProduct(ProductRequest request);
	
	ProductResponse updateProduct(Long id, ProductRequest request);
	
	ProductResponse getProductById(Long id);
	
	List<ProductResponse> getAllProducts();
	
	List<ProductResponse> getProductsByCategory(Long categoryId);
	
	void deleteProduct(Long id);
	
	
}
