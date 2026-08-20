package com.ecommerce.platform.exception;

public class InvalidInventoryStateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidInventoryStateException(String message) {
		super(message);
	}

}
