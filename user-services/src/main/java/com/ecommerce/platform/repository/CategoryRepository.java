package com.ecommerce.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	
	boolean existsByName(String name);
	
	Optional<Category> findByName(String name);
	
	

}
