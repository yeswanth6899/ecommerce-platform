package com.ecommerce.platform.exception;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.platform.response.ApiResponse;

import org.springframework.http.converter.HttpMessageNotReadableException;

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
	
	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleProductAlreadyExistsResponse(ProductAlreadyExistsException ex){
		
		return buildErrorResponse(
	            HttpStatus.CONFLICT,
	            ex.getMessage());
		
	}
	
	@ExceptionHandler(CategoryDeletionException.class)
	public ResponseEntity<ApiResponse<Void>> handleCategoryDeletionException(CategoryDeletionException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InventoryAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleInventoryAlreadyExistsException(InventoryAlreadyExistsException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
		
	}
	
	@ExceptionHandler(InventoryNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleInventoryNotFoundException(InventoryNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InventoryReservationNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleInventoryReservationNotFoundException(InventoryReservationNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InvalidInventoryStateException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidInventoryStatusException(InvalidInventoryStateException ex){
		
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InvalidInventoryReservationStateException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidInventoryReservationStatusException
															(InvalidInventoryReservationStateException ex){
		
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleCartNotFoundException(CartNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleCartItemNotFoundException(CartItemNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ApiResponse<Void>> handleInsufficientStockException(InsufficientStockException ex){
		
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleAddressNotFoundException(AddressNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(EmptyCartException.class)
	public ResponseEntity<ApiResponse<Void>> handleEmptyCartException(EmptyCartException ex){
		
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleOrderNotFoundException(OrderNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(PaymentAlreadyCompletedException.class)
	public ResponseEntity<ApiResponse<Void>> handlePaymentAlreadyCompletedException(PaymentAlreadyCompletedException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InvalidOrderStatusException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidOrderStatusException(InvalidOrderStatusException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(ShipmentNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleShipmentNotFoundException(ShipmentNotFoundException ex){
		
		return buildErrorResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(ShipmentStatusAlreadyUpdatedException.class)
	public ResponseEntity<ApiResponse<Void>> handleShipmentStatusAlreadyUpdatedException(ShipmentStatusAlreadyUpdatedException ex){
		
		return buildErrorResponse(
				HttpStatus.CONFLICT,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(InvalidShippingStatusException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidShippingStatusException(InvalidShippingStatusException ex){
		
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage()
				);
	}
	
	@ExceptionHandler(NotificationNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNotificationNotFoundException(NotificationNotFoundException ex) {
		
		return buildErrorResponse(
	            HttpStatus.NOT_FOUND,
	            ex.getMessage());
	}
	
	@ExceptionHandler(NotificationDeliveryNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNotificationDeliveryNotFoundException(NotificationDeliveryNotFoundException ex) {
		
		return buildErrorResponse(
	            HttpStatus.NOT_FOUND,
	            ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage()));

	    ApiResponse<Map<String, String>> response =
	            new ApiResponse<>(
	                    HttpStatus.BAD_REQUEST.value(),
	                    "Validation Failed",
	                    LocalDateTime.now(),
	                    errors);

	    return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
	        HttpMessageNotReadableException ex) {

	    return buildErrorResponse(
	            HttpStatus.BAD_REQUEST,
	            "Invalid request body.");
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
