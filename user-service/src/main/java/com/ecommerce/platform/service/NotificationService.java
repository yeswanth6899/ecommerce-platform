package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.NotificationResponse;
import com.ecommerce.platform.entity.NotificationType;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.User;

public interface NotificationService {
	
	//Customer Operations
	
	List<NotificationResponse> getMyNotifications();
	
	NotificationResponse getNotification(Long id);
	
	List<NotificationResponse> getUnreadNotifications();
	
	long getUnreadNotificationCount();
	
	void markAsRead(Long notificationId);
	
	void markAllAsRead();
	
	//Internal System Operations
	
	void createNotification(User user, Order order, NotificationType notificationType,
								String title, String message);
	

}
