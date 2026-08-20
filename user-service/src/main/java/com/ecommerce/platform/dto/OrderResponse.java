package com.ecommerce.platform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.platform.entity.OrderStatus;
import com.ecommerce.platform.entity.PaymentMethod;
import com.ecommerce.platform.entity.PaymentStatus;

public class OrderResponse {
	
	private Long orderId;
	private String orderNumber;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private PaymentMethod paymentMethod;
	private BigDecimal subtotal;
	private BigDecimal taxAmount;
	private BigDecimal shippingCost;
	private BigDecimal discountAmount;
	private BigDecimal totalAmount;
	private Integer totalQuantity;
	private ShippingAddressResponse shippingAddress;
	private List<OrderItemResponse> items;
	private LocalDateTime orderedAt;
	
	
	public OrderResponse() {
		
	}


	public OrderResponse(Long orderId, String orderNumber, OrderStatus orderStatus, PaymentStatus paymentStatus,
			PaymentMethod paymentMethod, BigDecimal subtotal, BigDecimal taxAmount, BigDecimal shippingCost,
			BigDecimal discountAmount, BigDecimal totalAmount, Integer totalQuantity,
			ShippingAddressResponse shippingAddress, List<OrderItemResponse> items, LocalDateTime orderedAt) {
		
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.orderedAt = orderedAt;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.subtotal = subtotal;
		this.taxAmount = taxAmount;
		this.shippingCost = shippingCost;
		this.discountAmount = discountAmount;
		this.totalAmount = totalAmount;
		this.totalQuantity = totalQuantity;
		this.shippingAddress = shippingAddress;
		this.items = items;

	}
	

	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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


	public BigDecimal getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}


	public BigDecimal getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}


	public BigDecimal getShippingCost() {
		return shippingCost;
	}


	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}


	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}


	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public ShippingAddressResponse getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(ShippingAddressResponse shippingAddress) {
		this.shippingAddress = shippingAddress;
	}


	public List<OrderItemResponse> getItems() {
		return items;
	}


	public void setItems(List<OrderItemResponse> items) {
		this.items = items;
	}


	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}


	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}
}
