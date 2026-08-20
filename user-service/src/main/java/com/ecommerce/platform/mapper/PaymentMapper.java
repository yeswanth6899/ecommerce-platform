package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.PaymentResponse;
import com.ecommerce.platform.entity.Payment;

@Component
public class PaymentMapper {

	public PaymentResponse toResponse(Payment payment) {
		
		PaymentResponse response = new PaymentResponse();
		
		response.setTransactionId(payment.getTransactionId());
		response.setOrderNumber(payment.getOrder().getOrderNumber());
		response.setPaymentStatus(payment.getPaymentStatus());
		response.setPaymentMethod(payment.getPaymentMethod());
		response.setAmount(payment.getAmount());
		response.setPaymentGateway(payment.getPaymentGateway());
		response.setPaidAt(payment.getCreatedAt());
		
		return response;

	}
}
