package com.ecommerce.platform.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.platform.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsResponse(UserAlreadyExistsException ex){
		
		return buildErrorResponse(
	            HttpStatus.CONFLICT,
	            ex.getMessage());
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiResponse<Void>>handleInvalidCredentialsException(InvalidCredentialsException ex){
		
		return buildErrorResponse(
	            HttpStatus.UNAUTHORIZED,
	            ex.getMessage());
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException ex){
		
		return buildErrorResponse(
	            HttpStatus.NOT_FOUND,
	            ex.getMessage());
		
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex){
		
		return buildErrorResponse(
	            HttpStatus.UNAUTHORIZED,
	            ex.getMessage());
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(InvalidPasswordException ex){
		
		return buildErrorResponse(
	            HttpStatus.BAD_REQUEST,
	            ex.getMessage());
		
	}
	
	@ExceptionHandler(CategoryAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleCategoryAlreadyExistsResponse(CategoryAlreadyExistsException ex){
		
		return buildErrorResponse(
	            HttpStatus.CONFLICT,
	            ex.getMessage());
		
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleCategoryNotFoundException(CategoryNotFoundException ex){
		
		return buildErrorResponse(
	            HttpStatus.NOT_FOUND,
	            ex.getMessage());
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleProductNotFoundException(ProductNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(CategoryDeletionException.class)
	public ResponseEntity<ApiResponse<Void>> handleCategoryDeletionException(CategoryDeletionException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
	}
	
	
	//Helper Methods
	private ResponseEntity<ApiResponse<Void>> buildErrorResponse(HttpStatus status,String message) {

	    ApiResponse<Void> response =
	            new ApiResponse<>(
	                    status.value(),
	                    message,
	                    LocalDateTime.now(),
	                    null);

	    return ResponseEntity.status(status)
	            .body(response);
	}
	
}
