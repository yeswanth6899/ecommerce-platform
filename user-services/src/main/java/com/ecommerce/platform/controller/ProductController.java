package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.ProductRequest;
import com.ecommerce.platform.dto.ProductResponse;
import com.ecommerce.platform.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;

	public ProductController(ProductService productService) {
	
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
		
		ProductResponse response = productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
		
//		ProductResponse response = productService.getProductById(id);
//		
//		return ResponseEntity.ok(response);
		
		return ResponseEntity.ok(productService.getProductById(id));
		
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		List<ProductResponse> response = productService.getAllProducts();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductResponse>> getProductByCategory(@PathVariable Long categoryId){
		
		List<ProductResponse> response = productService.getProductsByCategory(categoryId);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
		
		ProductResponse response = productService.updateProduct(id, request);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

	    productService.deleteProduct(id);

	    return ResponseEntity.noContent().build();
	}

}
