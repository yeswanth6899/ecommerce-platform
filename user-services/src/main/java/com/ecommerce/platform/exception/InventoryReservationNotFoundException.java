package com.ecommerce.platform.exception;

public class InventoryReservationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InventoryReservationNotFoundException(String message) {
		super(message);
	}

}
