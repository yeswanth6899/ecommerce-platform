package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.PaymentRequest;
import com.ecommerce.platform.dto.PaymentResponse;
import com.ecommerce.platform.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
	
	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		
		this.paymentService = paymentService;
	}
	
	@PostMapping
	public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
		
		PaymentResponse response = paymentService.processPayment(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
		
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<PaymentResponse> getPaymentByTransactionId(@PathVariable String transactionId){
		
		PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/order/{orderNumber}")
	public ResponseEntity<List<PaymentResponse>> getPaymentsByOrderNumber(@PathVariable String orderNumber){
		
		List<PaymentResponse> response = paymentService.getPaymentsByOrderNumber(orderNumber);
		
		return ResponseEntity.ok(response);
		
	}

}
