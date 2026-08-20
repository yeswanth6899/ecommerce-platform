package com.ecommerce.platform.exception;

public class ShipmentStatusAlreadyUpdatedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShipmentStatusAlreadyUpdatedException(String message) {
		super(message);
	}

}
