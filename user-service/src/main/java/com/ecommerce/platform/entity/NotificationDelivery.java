package com.ecommerce.platform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_deliveries")
public class NotificationDelivery extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_id", nullable = false)
	private Notification notification;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationChannel notificationChannel;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationDeliveryStatus notificationDeliveryStatus;
	
	@Column(nullable = false)
	private Integer retryCount = 0;
	
	@Column(columnDefinition = "TEXT")
	private String failureReason;
	private LocalDateTime sentAt;
	
	public NotificationDelivery() {
		
	}

	
	public NotificationDelivery(Notification notification, NotificationChannel notificationChannel,
			NotificationDeliveryStatus notificationDeliveryStatus) {
		
		this.notification = notification;
		this.notificationChannel = notificationChannel;
		this.notificationDeliveryStatus = notificationDeliveryStatus;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public NotificationChannel getNotificationChannel() {
		return notificationChannel;
	}

	public void setNotificationChannel(NotificationChannel notificationChannel) {
		this.notificationChannel = notificationChannel;
	}
	
	

	public NotificationDeliveryStatus getNotificationDeliveryStatus() {
		return notificationDeliveryStatus;
	}


	public void setNotificationDeliveryStatus(NotificationDeliveryStatus notificationDeliveryStatus) {
		this.notificationDeliveryStatus = notificationDeliveryStatus;
	}


	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
}
