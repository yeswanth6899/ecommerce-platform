package com.ecommerce.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
