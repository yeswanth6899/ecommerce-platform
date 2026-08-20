package com.ecommerce.platform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.NotificationResponse;
import com.ecommerce.platform.entity.Notification;
import com.ecommerce.platform.entity.NotificationChannel;
import com.ecommerce.platform.entity.NotificationDelivery;
import com.ecommerce.platform.entity.NotificationDeliveryStatus;
import com.ecommerce.platform.entity.NotificationType;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.exception.NotificationNotFoundException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.NotificationMapper;
import com.ecommerce.platform.repository.NotificationRepository;
import com.ecommerce.platform.repository.UserRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	private final UserRepository userRepository;
	private final NotificationRepository notificationRepository;
	private final NotificationMapper notificationMapper;
	
	private static final List<NotificationChannel> ALL_CHANNELS =
	        List.of(
	                NotificationChannel.EMAIL,
	                NotificationChannel.PUSH,
	                NotificationChannel.SMS);

	private static final List<NotificationChannel> EMAIL_ONLY =
	        List.of(NotificationChannel.EMAIL);

	
	
	public NotificationServiceImpl(UserRepository userRepository,NotificationRepository notificationRepository, 
			NotificationMapper notificationMapper) {
	
		this.userRepository = userRepository;
		this.notificationRepository = notificationRepository;
		this.notificationMapper = notificationMapper;
	}
	
	//Customer API's

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getMyNotifications() {
		
		User user = getAuthenticatedUser();
		
		return notificationRepository.findByUserOrderByCreatedAtDesc(user)
										.stream()
										.map(notificationMapper :: toResponse)
										.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public NotificationResponse getNotification(Long id) {
		
		User user = getAuthenticatedUser();
		
		Notification notification = getNotification(id, user);
		
		return notificationMapper.toResponse(notification);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getUnreadNotifications() {
		
		User user = getAuthenticatedUser();
		
		return notificationRepository.findByUserAndReadAtIsNullOrderByCreatedAtDesc(user)
										.stream()
										.map(notificationMapper :: toResponse)
										.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public long getUnreadNotificationCount() {
		
		return notificationRepository.countByUserAndReadAtIsNull(getAuthenticatedUser());
	}
	

	@Override
	public void markAsRead(Long notificationId) {
		
		User user = getAuthenticatedUser();
		
		Notification notification = getNotification(notificationId, user);
		
		markNotificationAsRead(notification);
		
	}
	
	@Override
	public void markAllAsRead() {
		
		User user = getAuthenticatedUser();
		
		List<Notification> notifications = notificationRepository
											.findByUserAndReadAtIsNullOrderByCreatedAtDesc(user);
		
		notifications.forEach(this :: markNotificationAsRead);
		
		
	}

	@Override
	public void createNotification(User user, Order order, NotificationType notificationType, String title,
			String message) {
		
		
		Notification notification = buildNotification(user, order, notificationType, title, message);
		
		List<NotificationChannel> channels = getNotificationChannels(notification.getNotificationType()); 
		
		createNotificationDeliveries(notification, channels);
		
		notificationRepository.save(notification);
		
	}
	
	
	
	//Helper Methods
	
	private User getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
								.orElseThrow(() -> new UserNotFoundException("User not found with the email: "
																				+ email));
	}
	
	private Notification getNotification(Long notificationId, User user) {
		
		return notificationRepository.findByIdAndUser(notificationId, user)
										.orElseThrow(() -> new NotificationNotFoundException("Notification not found with id: "
																									+ notificationId));
	}
	
	private Notification buildNotification(User user, Order order, NotificationType notificationType,
												String title, String message) {
		
		Notification notification = new Notification();
		
		notification.setUser(user);
		notification.setOrder(order);
		notification.setNotificationType(notificationType);
		notification.setTitle(title);
		notification.setMessage(message);
		
		return notification;
	}
	
	private NotificationDelivery buildNotificationDelivery(NotificationChannel channel) {
		
		NotificationDelivery delivery = new NotificationDelivery();
		
		delivery.setNotificationChannel(channel);
		delivery.setNotificationDeliveryStatus(NotificationDeliveryStatus.PENDING);
		
		return delivery;
	}
	
	private List<NotificationChannel> getNotificationChannels(NotificationType notificationType) {
		
		switch(notificationType) {
		
		case WELCOME:
            return EMAIL_ONLY;

        case PAYMENT_SUCCESS:
        case PAYMENT_FAILED:
        case SHIPMENT_DELIVERED:
        	
            return ALL_CHANNELS;

        default:
            return EMAIL_ONLY;
		}
	}
	
	private void createNotificationDeliveries(Notification notification, List<NotificationChannel> channels) {
		
		for(NotificationChannel channel : channels) {
			
			NotificationDelivery delivery = buildNotificationDelivery(channel);
			
			notification.addDelivery(delivery);
		}
	}
	
	
	private void markNotificationAsRead(Notification notification) {
		
		if(notification.getReadAt() == null) {
			
			notification.setReadAt(LocalDateTime.now());
		}
	}

}
