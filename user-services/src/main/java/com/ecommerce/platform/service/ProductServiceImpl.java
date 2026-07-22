package com.ecommerce.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.platform.dto.ProductRequest;
import com.ecommerce.platform.dto.ProductResponse;
import com.ecommerce.platform.entity.Category;
import com.ecommerce.platform.entity.Product;
import com.ecommerce.platform.exception.CategoryNotFoundException;
import com.ecommerce.platform.exception.ProductNotFoundException;
import com.ecommerce.platform.mapper.ProductMapper;
import com.ecommerce.platform.repository.CategoryRepository;
import com.ecommerce.platform.repository.ProductRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ProductMapper productMapper;
	
	
	

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,ProductMapper productMapper) {
		
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.productMapper = productMapper;
	}

	@Override
	@Transactional
	public ProductResponse createProduct(ProductRequest request) {
		
		Category category = fetchCategoryById(request.getCategoryId());
		Product product = productMapper.toEntity(request, category);
		
		Product savedProduct = productRepository.save(product);
		
		return productMapper.toResponse(savedProduct);
	}


	@Override
	@Transactional(readOnly = true)
	public ProductResponse getProductById(Long id) {
		
		Product product = fetchProductById(id);
			
		return productMapper.toResponse(product);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProducts() {
		
		return productRepository.findAll()
								.stream()
								.map(productMapper::toResponse)
								.toList();
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> getProductsByCategory(Long categoryId) {
		
		fetchCategoryById(categoryId);
	
		return productRepository.findByCategoryId(categoryId)
								.stream()
								.map(productMapper::toResponse)
								.toList();
	}
	
	
	@Override
	@Transactional
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		
		Product product = fetchProductById(id);
		Category category = fetchCategoryById(request.getCategoryId());
		
		productMapper.updateEntity(product, request, category);
		
		Product updatedProduct = productRepository.save(product);
		
		return productMapper.toResponse(updatedProduct);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		
		Product product = fetchProductById(id);
		productRepository.delete(product);
		
	}
	
	//Helper Methods:
	
	private Category fetchCategoryById(Long categoryId) {
	    return categoryRepository.findById(categoryId)
	            .orElseThrow(() ->
	                    new CategoryNotFoundException(
	                            "Category not found with id: " + categoryId));
	}
	
	private Product fetchProductById(Long id) {
		
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not founds id:" + id));
	}

	

}
