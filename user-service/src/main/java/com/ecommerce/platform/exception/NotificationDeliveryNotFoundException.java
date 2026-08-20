package com.ecommerce.platform.exception;

public class NotificationDeliveryNotFoundException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotificationDeliveryNotFoundException(String message) {
		super(message);
	}

}
