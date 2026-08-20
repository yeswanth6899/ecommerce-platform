package com.ecommerce.platform.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.PaymentRequest;
import com.ecommerce.platform.dto.PaymentResponse;
import com.ecommerce.platform.entity.NotificationType;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.OrderStatus;
import com.ecommerce.platform.entity.Payment;
import com.ecommerce.platform.entity.PaymentStatus;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.exception.InvalidOrderStatusException;
import com.ecommerce.platform.exception.OrderNotFoundException;
import com.ecommerce.platform.exception.PaymentAlreadyCompletedException;
import com.ecommerce.platform.exception.PaymentNotFoundException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.PaymentMapper;
import com.ecommerce.platform.repository.OrderRepository;
import com.ecommerce.platform.repository.PaymentRepository;
import com.ecommerce.platform.repository.UserRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final PaymentMapper paymentMapper;
	private static final int PAYMENT_SUCCESS_RATE = 80;
	private static final Random RANDOM = new Random();
	private final NotificationService notificationService;
	private final InventoryService inventoryService;
	private final ShipmentService shipmentService;
	
	

	public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository,
			OrderRepository orderRepository, PaymentMapper paymentMapper, NotificationService notificationService,
			InventoryService inventoryService, ShipmentService shipmentService) {
	
		this.paymentRepository = paymentRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.paymentMapper = paymentMapper;
		this.notificationService = notificationService;
		this.inventoryService = inventoryService;
		this.shipmentService = shipmentService;
	}

	@Override
	@Transactional
	public PaymentResponse processPayment(PaymentRequest request) {
		
		User user  = getAuthenticatedUser();
		
		Order order = getOrder(request.getOrderNumber(), user);
		
		validateOrderForPayment(order);
		
		prepareOrderForPayment(order); //when payment fails the order will again prepare for payment so we set the inventory reservation status from Released to Reserved again and we will again reserve the stock and allow the user to retry the payment again 
		
		Payment payment = createPayment(order);
		
		PaymentStatus paymentStatus = processMockPayment();
		
		updatePayment(payment, paymentStatus);
		updateOrder(order, paymentStatus);
		handleInventoryReservation(order, paymentStatus);
		
		//orderRepository.save(order);
		
		Payment savedPayment = paymentRepository.save(payment);
		
		handleShipmentCreation(order, paymentStatus);
		createPaymentNotification(user, order, paymentStatus);
		
		return paymentMapper.toResponse(savedPayment);
		
	}

	@Override
	@Transactional(readOnly = true)
	public PaymentResponse getPaymentByTransactionId(String transactionId) {
		
		User user = getAuthenticatedUser();
		
		Payment payment = paymentRepository.findByTransactionIdAndOrderUser(transactionId, user)
											.orElseThrow(() -> new PaymentNotFoundException("Payment not found with the transaction id: " + transactionId));
		
		return paymentMapper.toResponse(payment);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaymentResponse> getPaymentsByOrderNumber(String orderNumber) {
		
		User user = getAuthenticatedUser();
		
		Order order = getOrder(orderNumber, user);
		
		return paymentRepository.findByOrderOrderByCreatedAtDesc(order)
								.stream()
								.map(paymentMapper :: toResponse)
								.toList();
	}
	
	
	//Helper Methods
	
	private User getAuthenticatedUser() {
		
		Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
		        				.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
	}
	
	private Order getOrder(String orderNumber, User user) {
		
		return orderRepository.findByOrderNumberAndUser(orderNumber, user)
								.orElseThrow(() -> new OrderNotFoundException(
																"Order not found with order number: " + orderNumber));
	}
	
	private void validateOrderForPayment(Order order) {
		
		if(order.getPaymentStatus() == PaymentStatus.PAID) {
			
			throw new PaymentAlreadyCompletedException("Payment has already been completed for this order.");
		}
		
		if(order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.PAYMENT_FAILED) {
			
			throw new InvalidOrderStatusException("Cannot process payment for an order with status: "
		            												+ order.getOrderStatus());
		}
	}
	
	private String generateTransactionId() {
		
		String currentDate = LocalDate.now()
										.format(DateTimeFormatter.BASIC_ISO_DATE);
		
		String randomSuffix = UUID.randomUUID()
									.toString()
									.substring(0, 6)
									.toUpperCase();
		
		return "TXN-" + currentDate + "-" + randomSuffix;
	}
	
	private Payment createPayment(Order order) {
		
		Payment payment = new Payment();
		
		payment.setOrder(order);
		payment.setTransactionId(generateTransactionId());
		payment.setPaymentMethod(order.getPaymentMethod());
		payment.setPaymentStatus(PaymentStatus.PENDING);
		payment.setAmount(order.getTotalAmount());
		payment.setPaymentGateway("MOCK");
		
		return payment;
	}
	
	private PaymentStatus processMockPayment() {
	
		int randomNumber = RANDOM.nextInt(100);
		
		if(randomNumber < PAYMENT_SUCCESS_RATE) {
			
			return PaymentStatus.PAID;
			
		}
		
		return PaymentStatus.FAILED;
	}
	
	private void updatePayment(Payment payment, PaymentStatus paymentStatus) {
		
		payment.setPaymentStatus(paymentStatus);
		
		if(paymentStatus == PaymentStatus.PAID) {
			
			payment.setGatewayResponse("Payment processed successfully.");
			payment.setFailureReason(null);
			return;
		}
			
			payment.setGatewayResponse("Payment processing failed.");
			payment.setFailureReason("Insufficient balance");
	
	}
	
	private void updateOrder(Order order, PaymentStatus paymentStatus) {
		
		order.setPaymentStatus(paymentStatus);
		
		if(paymentStatus == PaymentStatus.PAID) {
			
			order.setOrderStatus(OrderStatus.CONFIRMED);
			return;
		}
		
		order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
	}
	
	private void createPaymentNotification(User user,Order order, PaymentStatus paymentStatus) {

			switch (paymentStatus) {

				case PAID:

					notificationService.createNotification(user,
														   order,
														   NotificationType.PAYMENT_SUCCESS,
														   "Payment Successful",
														   "Your payment for Order #" + order.getOrderNumber()
														   + " was successful.");
					break;

				case FAILED:

					notificationService.createNotification(user,
														   order,
														   NotificationType.PAYMENT_FAILED,
														   "Payment Failed",
														   "Your payment for Order #" + order.getOrderNumber()
														   + " could not be processed.");
					break;

				default:

					throw new IllegalArgumentException("Unsupported payment status: " + paymentStatus);
			}
	}
	
	private void handleInventoryReservation(Order order, PaymentStatus paymentStatus) {
		
		switch(paymentStatus) {
		
		case PAID:
			
			inventoryService.confirmReservation(order);
			break;
			
		case FAILED:
			
			inventoryService.releaseReservation(order);
			break;
			
		default:
			break;
		}
	}
	
	private void handleShipmentCreation(Order order, PaymentStatus paymentStatus) {

		if (paymentStatus == PaymentStatus.PAID) {

			shipmentService.createShipment(order);
		}
	}
	
	private void prepareOrderForPayment(Order order) {

	    if (order.getOrderStatus() == OrderStatus.PAYMENT_FAILED) {

	        inventoryService.reserveStock(order);
	    }
	}
	
	

}
