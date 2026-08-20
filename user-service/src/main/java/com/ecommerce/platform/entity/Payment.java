package com.ecommerce.platform.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@Column(nullable  = false, unique = true)
	private String transactionId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable  = false)
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable  = false)
	private PaymentStatus paymentStatus;
	
	@Column(nullable  = false, precision = 10, scale = 2)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private String paymentGateway;
	
	private String gatewayResponse;
	private String failureReason;
	
	
	public Payment() {
		
	}


	public Payment(Order order, String transactionId, PaymentMethod paymentMethod, PaymentStatus paymentStatus,
			BigDecimal amount, String paymentGateway, String gatewayResponse, String failureReason) {
		
		this.order = order;
		this.transactionId = transactionId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.amount = amount;
		this.paymentGateway = paymentGateway;
		this.gatewayResponse = gatewayResponse;
		this.failureReason = failureReason;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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


	public String getGatewayResponse() {
		return gatewayResponse;
	}


	public void setGatewayResponse(String gatewayResponse) {
		this.gatewayResponse = gatewayResponse;
	}


	public String getFailureReason() {
		return failureReason;
	}


	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}
