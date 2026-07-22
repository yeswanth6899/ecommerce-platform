package com.ecommerce.platform.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
		
		List<Product> findByCategoryId(Long categoryId);
		
		boolean existsByCategoryId(Long categoryId);

}
