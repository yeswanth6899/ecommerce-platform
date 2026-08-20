package com.ecommerce.platform.response;

import java.time.LocalDateTime;

public record ApiResponse <T>(
	int status,
	String message,
	LocalDateTime timestamp,
	T data

){
	
}
