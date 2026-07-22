package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.CategoryRequest;
import com.ecommerce.platform.dto.CategoryResponse;


public interface CategoryService {
	
	CategoryResponse createCategory(CategoryRequest request);
	
	List<CategoryResponse> getAllCategories();
	
	CategoryResponse getCategoryById(Long id);
	
	CategoryResponse updateCategory(Long id, CategoryRequest request);
	
	void deleteCategory(Long id);

}
