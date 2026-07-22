package com.ecommerce.platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.CategoryRequest;
import com.ecommerce.platform.dto.CategoryResponse;
import com.ecommerce.platform.entity.Category;
import com.ecommerce.platform.exception.CategoryAlreadyExistsException;
import com.ecommerce.platform.exception.CategoryDeletionException;
import com.ecommerce.platform.exception.CategoryNotFoundException;
import com.ecommerce.platform.mapper.CategoryMapper;
import com.ecommerce.platform.repository.CategoryRepository;
import com.ecommerce.platform.repository.ProductRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final ProductRepository productRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
			ProductRepository productRepository) {
		
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
		this.productRepository = productRepository;
	}



	@Override
	@Transactional
	public CategoryResponse createCategory(CategoryRequest request) {
		
		if (categoryRepository.existsByName(request.getName())) {
			
			throw new CategoryAlreadyExistsException("Category already exists");
		}
		
		Category category = categoryMapper.toEntity(request);
		
		Category savedCategory = categoryRepository.save(category);
		
		return categoryMapper.toResponse(savedCategory);
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public List<CategoryResponse> getAllCategories() {
		
		return categoryRepository.findAll()
		        .stream()
		        .map(categoryMapper::toResponse)
		        .toList();
		
	}

	
	@Override
	@Transactional(readOnly = true)
	public CategoryResponse getCategoryById(Long id) {
		
		Category category = fetchCategoryById(id);

		return categoryMapper.toResponse(category);
	}

	
	
	@Override
	@Transactional
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		
		Category category = fetchCategoryById(id);
		
//		Category categoryWithSameName = categoryRepository
//		        .findByName(request.getName())
//		        .orElse(null);
//
//		if (categoryWithSameName != null
//		        && !category.getId().equals(categoryWithSameName.getId())) {}
		
		Optional<Category> categoryWithSameName = categoryRepository.findByName(request.getName());
	
		
		if (categoryWithSameName.isPresent() && !category.getId().equals(categoryWithSameName.get().getId())) {

		    throw new CategoryAlreadyExistsException(
		            "Category already exists with the name: "
		            + request.getName());
		}
		
		categoryMapper.updateEntity(category, request);
		
		Category savedCategory = categoryRepository.save(category);
		
		
		return categoryMapper.toResponse(savedCategory);
	}

	
	
	@Override
	@Transactional
	public void deleteCategory(Long id) {
		
		Category category = fetchCategoryById(id);
		
		if(productRepository.existsByCategoryId(id)) {
			
			throw new CategoryDeletionException("Cannot delete category because it contains products.");
		}
		
		categoryRepository.delete(category);
		
	}
	
	//Helper Methods
	
	private Category fetchCategoryById(Long id) {
		
		return categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found with id:" + id));
	}
	
//	private CategoryResponse mapToResponse(Category category) {
//  return new CategoryResponse(
//          category.getId(),
//          category.getName(),
//          category.getDescription()
//  );
//}
	
//	private Category mapToEntity(CategoryRequest request) {
//        return new Category(
//                request.getName(),
//                request.getDescription()
//        );
//    }

}
