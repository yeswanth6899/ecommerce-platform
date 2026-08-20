package com.ecommerce.platform.exception;

public class InvalidInventoryReservationStateException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidInventoryReservationStateException(String message) {
		super(message);
	}

}
