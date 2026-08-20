package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.NotificationResponse;
import com.ecommerce.platform.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
	
	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
	
		this.notificationService = notificationService;
	}
	
	@GetMapping
	public ResponseEntity<List<NotificationResponse>> getMyNotifications() {
		
		List<NotificationResponse> response = notificationService.getMyNotifications();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
		
		NotificationResponse response = notificationService.getNotification(id);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/unread")
	public ResponseEntity<List<NotificationResponse>> getUnreadNotifications() {
		
		List<NotificationResponse> response = notificationService.getUnreadNotifications();
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/unread/count")
	public ResponseEntity<Long> getUnreadNotificationCount(){
		
		long response = notificationService.getUnreadNotificationCount();
		
		return ResponseEntity.ok(response);
		
	}
	
	@PatchMapping("/{notificationId}/read")
	public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId){
		
		notificationService.markAsRead(notificationId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/read-all")
	public ResponseEntity<Void> markAllAsRead() {

	    notificationService.markAllAsRead();

	    return ResponseEntity.noContent().build();
	}

}
