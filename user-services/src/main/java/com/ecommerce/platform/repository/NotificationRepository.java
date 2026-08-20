package com.ecommerce.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Notification;
import com.ecommerce.platform.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	Optional<Notification> findByIdAndUser(Long id, User user);
	
	List<Notification> findByUserOrderByCreatedAtDesc(User user);
	
	List<Notification> findByUserAndReadAtIsNullOrderByCreatedAtDesc(User user);
	
	long countByUserAndReadAtIsNull(User user);

}
