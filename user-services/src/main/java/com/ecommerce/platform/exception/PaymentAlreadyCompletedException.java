package com.ecommerce.platform.exception;

public class PaymentAlreadyCompletedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PaymentAlreadyCompletedException(String message) {
		super(message);
	}

}
