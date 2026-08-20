package com.ecommerce.platform.dto;

import java.time.LocalDateTime;

import com.ecommerce.platform.entity.NotificationType;

public class NotificationResponse {
	
	
	private Long id;
	private String orderNumber;
	private NotificationType notificationType;
	private String title;
	private String message;
	private boolean isRead;
	private LocalDateTime createdAt;
	
	
	public NotificationResponse() {
		
	}


	public NotificationResponse(Long id, String orderNumber, NotificationType notificationType, String title,
			String message, boolean isRead, LocalDateTime createdAt) {
		
		this.id = id;
		this.orderNumber = orderNumber;
		this.notificationType = notificationType;
		this.title = title;
		this.message = message;
		this.isRead = isRead;
		this.createdAt = createdAt;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public NotificationType getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public boolean isRead() {
		return isRead;
	}


	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
