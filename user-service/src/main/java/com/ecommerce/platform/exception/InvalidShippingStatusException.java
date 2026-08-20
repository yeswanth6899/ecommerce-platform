package com.ecommerce.platform.exception;

public class InvalidShippingStatusException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidShippingStatusException(String message) {
		super(message);
	}

}
