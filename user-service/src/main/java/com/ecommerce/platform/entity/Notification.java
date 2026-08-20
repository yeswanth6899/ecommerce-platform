package com.ecommerce.platform.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationType notificationType;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;
	
	private LocalDateTime readAt;
	
	@OneToMany(mappedBy = "notification",
				cascade = CascadeType.ALL,
				orphanRemoval = true)
	private List<NotificationDelivery> deliveries = new ArrayList<>();
	
	
	public Notification() {
		
	}


	public Notification(User user, Order order, NotificationType notificationType,
			String title, String message, LocalDateTime readAt) {
		
		this.user = user;
		this.order = order;
		this.notificationType = notificationType;
		this.title = title;
		this.message = message;
		this.readAt = readAt;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
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


	public LocalDateTime getReadAt() {
		return readAt;
	}


	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}


	public List<NotificationDelivery> getDeliveries() {
		return deliveries;
	}

	
	//Helper Methods
	
	public void addDelivery(NotificationDelivery delivery) {
		
		deliveries.add(delivery);
		delivery.setNotification(this);
	}
	
	public void removeDelivery(NotificationDelivery delivery) {
		
		deliveries.remove(delivery);
		delivery.setNotification(null);
	}
	
}
