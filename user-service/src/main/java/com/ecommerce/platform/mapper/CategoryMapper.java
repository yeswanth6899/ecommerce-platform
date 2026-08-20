package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.CategoryRequest;
import com.ecommerce.platform.dto.CategoryResponse;
import com.ecommerce.platform.entity.Category;

@Component
public class CategoryMapper {
	
	public Category toEntity(CategoryRequest request) {
		
		Category category = new Category();
		
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		
		return category;
	}
	
	public CategoryResponse toResponse(Category category) {
		
		CategoryResponse response = new CategoryResponse();
		
		response.setId(category.getId());
		response.setName(category.getName());
		response.setDescription(category.getDescription());
		
		return response;
	}
	
	public void updateEntity(Category existingCategory, CategoryRequest request) {
		
		existingCategory.setName(request.getName());
		existingCategory.setDescription(request.getDescription());
	}

}
