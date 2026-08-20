package com.ecommerce.platform.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String orderNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod paymentMethod;
	
	@Column(nullable = false, precision = 10, scale =2)
	private BigDecimal subtotal;
	
	@Column(nullable = false, precision = 10, scale =2)
	private BigDecimal taxAmount;
	
	@Column(nullable = false, precision = 10, scale =2)
	private BigDecimal shippingCost;
	
	@Column(nullable = false, precision = 10, scale =2)
	private BigDecimal discountAmount;
	
	@Column(nullable = false, precision = 10, scale =2)
	private BigDecimal totalAmount;
	
	@Column(nullable = false)
	private String shippingFullName;
	
	@Column(nullable = false)
	private String shippingPhoneNumber;
	
	@Column(nullable = false)
	private String shippingAddressLine1;
	
	private String shippingAddressLine2;
	
	@Column(nullable = false)
	private String shippingCity;
	
	@Column(nullable = false)
	private String shippingState;
	
	@Column(nullable = false)
	private String shippingPostalCode;
	
	@Column(nullable = false)
	private String shippingCountry;
	
	@OneToMany(mappedBy = "order",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@Column(nullable = false)
	private Integer totalQuantity;
	
	@OneToMany(mappedBy = "order",
				cascade = CascadeType.ALL,
				orphanRemoval = true)
	private List<Shipment> shipments = new ArrayList<>();
	
	
	public Order() {
		
	}

	public Order(String orderNumber, User user, OrderStatus orderStatus, PaymentStatus paymentStatus,
			PaymentMethod paymentMethod, BigDecimal subtotal, BigDecimal taxAmount, BigDecimal shippingCost,
			BigDecimal discountAmount, BigDecimal totalAmount, String shippingFullName, String shippingPhoneNumber,
			String shippingAddressLine1, String shippingAddressLine2, String shippingCity, String shippingState,
			String shippingPostalCode, String shippingCountry, Integer totalQuantity) {
		
		this.orderNumber = orderNumber;
		this.user = user;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.subtotal = subtotal;
		this.taxAmount = taxAmount;
		this.shippingCost = shippingCost;
		this.discountAmount = discountAmount;
		this.totalAmount = totalAmount;
		this.shippingFullName = shippingFullName;
		this.shippingPhoneNumber = shippingPhoneNumber;
		this.shippingAddressLine1 = shippingAddressLine1;
		this.shippingAddressLine2 = shippingAddressLine2;
		this.shippingCity = shippingCity;
		this.shippingState = shippingState;
		this.shippingPostalCode = shippingPostalCode;
		this.shippingCountry = shippingCountry;
		this.totalQuantity = totalQuantity;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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


	public String getShippingFullName() {
		return shippingFullName;
	}


	public void setShippingFullName(String shippingFullName) {
		this.shippingFullName = shippingFullName;
	}


	public String getShippingPhoneNumber() {
		return shippingPhoneNumber;
	}


	public void setShippingPhoneNumber(String shippingPhoneNumber) {
		this.shippingPhoneNumber = shippingPhoneNumber;
	}


	public String getShippingAddressLine1() {
		return shippingAddressLine1;
	}


	public void setShippingAddressLine1(String shippingAddressLine1) {
		this.shippingAddressLine1 = shippingAddressLine1;
	}


	public String getShippingAddressLine2() {
		return shippingAddressLine2;
	}


	public void setShippingAddressLine2(String shippingAddressLine2) {
		this.shippingAddressLine2 = shippingAddressLine2;
	}


	public String getShippingCity() {
		return shippingCity;
	}


	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}


	public String getShippingState() {
		return shippingState;
	}


	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}


	public String getShippingPostalCode() {
		return shippingPostalCode;
	}


	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}


	public String getShippingCountry() {
		return shippingCountry;
	}


	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}


	public List<OrderItem> getOrderItems() {
		return orderItems;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	
	public List<Shipment> getShipments() {
		return shipments;
	}
	
	
	
	//Helper Methods
	
	public void addOrderItem(OrderItem item) {
		
		orderItems.add(item);
		item.setOrder(this);
	}
	
	public void removeOrderItem(OrderItem item) {
		
		orderItems.remove(item);
		item.setOrder(null);
	}
	
	public void addShipment(Shipment shipment) {
		
		if(!shipments.contains(shipment)) {
			
			shipments.add(shipment);
			shipment.setOrder(this);
		}
	}
	
	public void removeShipment(Shipment shipment) {
		
		shipments.remove(shipment);
		shipment.setOrder(null);
	}

}
