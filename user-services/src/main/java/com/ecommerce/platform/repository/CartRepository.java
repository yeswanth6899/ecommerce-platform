package com.ecommerce.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
	Optional<Cart> findByUser(User user);

}
