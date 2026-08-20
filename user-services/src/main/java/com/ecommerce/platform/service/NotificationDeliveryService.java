package com.ecommerce.platform.service;



public interface NotificationDeliveryService {
	
	void processDelivery(Long deliveryId);
	
	void recoverStuckDelivery(Long deliveryId);

}
