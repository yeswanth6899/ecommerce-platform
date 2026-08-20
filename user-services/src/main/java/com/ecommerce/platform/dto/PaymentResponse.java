package com.ecommerce.platform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.platform.entity.PaymentMethod;
import com.ecommerce.platform.entity.PaymentStatus;

public class PaymentResponse {
	
	private String transactionId;
	private String orderNumber;
	private PaymentStatus paymentStatus;
	private PaymentMethod paymentMethod;
	private BigDecimal amount;
	private String paymentGateway;
	private LocalDateTime paidAt;
	
	
	public PaymentResponse() {
	
	}


	public PaymentResponse(String transactionId, String orderNumber, PaymentStatus paymentStatus,
			PaymentMethod paymentMethod, BigDecimal amount, String paymentGateway, LocalDateTime paidAt) {
		
		this.transactionId = transactionId;
		this.orderNumber = orderNumber;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentGateway = paymentGateway;
		this.paidAt = paidAt;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getPaymentGateway() {
		return paymentGateway;
	}


	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}


	public LocalDateTime getPaidAt() {
		return paidAt;
	}


	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}
}
