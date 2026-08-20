package com.ecommerce.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.CartItem;
import com.ecommerce.platform.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
	
	Optional<CartItem> findByIdAndCart(Long cartItemId, Cart cart);
	
	void deleteByCart(Cart cart);

}
