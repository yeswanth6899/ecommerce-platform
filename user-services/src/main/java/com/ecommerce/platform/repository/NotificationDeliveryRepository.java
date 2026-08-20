package com.ecommerce.platform.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.NotificationDelivery;
import com.ecommerce.platform.entity.NotificationDeliveryStatus;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, Long> {
	
	List<NotificationDelivery> findByNotificationDeliveryStatus(NotificationDeliveryStatus status);
	
	List<NotificationDelivery> findByNotificationDeliveryStatusAndUpdatedAtBefore
											(NotificationDeliveryStatus status, LocalDateTime cutoffTime);
	

}
