package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.PaymentRequest;
import com.ecommerce.platform.dto.PaymentResponse;

public interface PaymentService {
	
	PaymentResponse processPayment(PaymentRequest request);
	
	PaymentResponse getPaymentByTransactionId(String transactionId);
	
	List<PaymentResponse> getPaymentsByOrderNumber(String orderNumber);

}
