package com.ecommerce.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.Payment;
import com.ecommerce.platform.entity.User;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	Optional<Payment> findByTransactionId(String transactionId);
	
	Optional<Payment> findByTransactionIdAndOrderUser(String transactionId,User user);
	
	List<Payment> findByOrderOrderByCreatedAtDesc(Order order);

}
