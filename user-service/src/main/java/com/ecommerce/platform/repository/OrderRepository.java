package com.ecommerce.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	
	List<Order> findByUserOrderByCreatedAtDesc(User user);
	
	Optional<Order> findByOrderNumber(String orderNumber);
	
	Optional<Order> findByOrderNumberAndUser(String orderNumber, User user);
}
