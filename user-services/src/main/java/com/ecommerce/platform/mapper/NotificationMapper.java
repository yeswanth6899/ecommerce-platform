package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.NotificationResponse;
import com.ecommerce.platform.entity.Notification;

@Component
public class NotificationMapper {
	
	public NotificationResponse toResponse(Notification notification) {
		
		NotificationResponse response = new NotificationResponse();
		
		response.setId(notification.getId());
		response.setOrderNumber(notification.getOrder() != null 
													? notification.getOrder().getOrderNumber() : null);
		response.setNotificationType(notification.getNotificationType());
		response.setTitle(notification.getTitle());
		response.setMessage(notification.getMessage());
		response.setRead(notification.getReadAt() != null);
		response.setCreatedAt(notification.getCreatedAt());
		
		return response;
	}

}
