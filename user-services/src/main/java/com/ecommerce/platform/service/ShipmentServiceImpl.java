package com.ecommerce.platform.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.ShipmentResponse;
import com.ecommerce.platform.entity.NotificationType;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.OrderStatus;
import com.ecommerce.platform.entity.Shipment;
import com.ecommerce.platform.entity.ShippingCarrier;
import com.ecommerce.platform.entity.ShippingStatus;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.exception.InvalidShippingStatusException;
import com.ecommerce.platform.exception.OrderNotFoundException;
import com.ecommerce.platform.exception.ShipmentNotFoundException;
import com.ecommerce.platform.exception.ShipmentStatusAlreadyUpdatedException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.ShipmentMapper;
import com.ecommerce.platform.repository.OrderRepository;
import com.ecommerce.platform.repository.ShipmentRepository;
import com.ecommerce.platform.repository.UserRepository;


@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService{
	
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final ShipmentRepository shipmentRepository;
	private final ShipmentMapper shipmentMapper;
	private static final Random RANDOM = new Random();
	private static final int ESTIMATED_DELIVERY_DAYS = 5;
	private final NotificationService notificationService;
	

	

	public ShipmentServiceImpl(UserRepository userRepository, OrderRepository orderRepository,
			ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper,
			NotificationService notificationService) {
		
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.shipmentRepository = shipmentRepository;
		this.shipmentMapper = shipmentMapper;
		this.notificationService = notificationService;
	}

	@Override
	public void createShipment(Order order) {
		
		Shipment shipment = buildShipment(order);

		shipmentRepository.save(shipment);
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public ShipmentResponse getShipmentByTrackingNumber(String trackingNumber) {
		
		User user = getAuthenticatedUser();
		
		Shipment shipment = getShipment(trackingNumber, user);
		
		return shipmentMapper.toResponse(shipment);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShipmentResponse> getShipmentByOrderNumber(String orderNumber) {
		
		User user = getAuthenticatedUser();
		Order order = getOrder(orderNumber, user);
		
		return shipmentRepository.findByOrderOrderByCreatedAtDesc(order)
									.stream()
									.map(shipmentMapper :: toResponse)
									.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShipmentResponse> getMyShipments() {
		
		User user = getAuthenticatedUser();
		
		return shipmentRepository.findByOrderUserOrderByCreatedAtDesc(user)
									.stream()
									.map(shipmentMapper :: toResponse)
									.toList();
		
	}
		
	@Override
	public ShipmentResponse adminUpdateShippingStatus(String trackingNumber, ShippingStatus newStatus) {
		
		Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
												.orElseThrow(() -> new ShipmentNotFoundException(
							                            				"Shipment not found with tracking number: "
							                            							+ trackingNumber));
		
		validateShippingStatusTransition(shipment.getShippingStatus(), newStatus);
		
		updateShipmentStatus(shipment, newStatus);
		
		Order order = shipment.getOrder();
		updateOrderStatus(order, newStatus);
		
		//Shipment savedShipment = shipmentRepository.save(shipment);
		
		handleShipmentNotification(shipment, newStatus);
		
		return shipmentMapper.toResponse(shipment);
	}
	
	
	// TODO: Publish Shipment Status Updated Event
	
	//Helper Methods
	
	private User getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
								.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
	}
	
	private Order getOrder(String orderNumber, User user) {
		
		return orderRepository.findByOrderNumberAndUser(orderNumber, user)
								.orElseThrow(() -> new OrderNotFoundException("Order not found with the order number" + orderNumber));
	}
	
	private Shipment getShipment(String trackingNumber, User user) {
		
		return shipmentRepository.findByTrackingNumberAndOrderUser(trackingNumber, user)
									.orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with the tracking number: " + trackingNumber));
	}
	
	private String generateTrackingNumber() {
		
		String currentDate = LocalDate.now()
										.format(DateTimeFormatter.BASIC_ISO_DATE);
		
		String randomSuffix = UUID.randomUUID()
									.toString()
									.substring(0, 6)
									.toUpperCase();
		
		return "TRK-" + currentDate + "-" + randomSuffix;
		
	}
	
	private ShippingCarrier assignCarrier() {
		
		ShippingCarrier[] carriers = ShippingCarrier.values();
		
		return carriers[RANDOM.nextInt(carriers.length)];
		
	}
	
	private LocalDate calculateEstimatedDeliveryDate() {
		
		return LocalDate.now().plusDays(ESTIMATED_DELIVERY_DAYS);
	}
	
	private void validateShippingStatusTransition(ShippingStatus currentStatus, ShippingStatus newStatus) {
		
		if(currentStatus == newStatus) {
			
			throw new ShipmentStatusAlreadyUpdatedException("Shipment is already in " + currentStatus + " status.");
		}
		
		switch(currentStatus) {
		
		case CREATED:
			
			if(newStatus != ShippingStatus.PACKED) {
				
				throwInvalidShippingStatusException(currentStatus, newStatus);
			}
			break;
			
		case PACKED:
			
			if(newStatus != ShippingStatus.SHIPPED) {
				
				throwInvalidShippingStatusException(currentStatus, newStatus);
			}
			break;
			
		case SHIPPED:
			
			if(newStatus != ShippingStatus.OUT_FOR_DELIVERY) {
				
				throwInvalidShippingStatusException(currentStatus, newStatus);
			}
			break;
			
		case OUT_FOR_DELIVERY:
			
			if(newStatus != ShippingStatus.DELIVERED) {
				
				throwInvalidShippingStatusException(currentStatus, newStatus);
			}
			break;
		
		case DELIVERED:
			
			throw new InvalidShippingStatusException("Delivered shipment cannot be updated.");
		
		default:
			
			throw new InvalidShippingStatusException("Invalid shipping status.");
			
		}	
	}
	
	private void throwInvalidShippingStatusException(ShippingStatus currentStatus,ShippingStatus newStatus) {

	    throw new InvalidShippingStatusException("Cannot change shipment status from " + currentStatus + " to " 
	    												+ newStatus);
	}
	
	private void updateShipmentStatus(Shipment shipment, ShippingStatus newStatus) {
		
		shipment.setShippingStatus(newStatus);
		
		switch(newStatus) {
		
		case SHIPPED:
			
			shipment.setShippedAt(LocalDateTime.now());
		
		break;
		
		case DELIVERED:
			
			shipment.setDeliveredAt(LocalDateTime.now());
		
		break;
		
		default:
			break;
		
		}
	}
	
	private void updateOrderStatus(Order order, ShippingStatus shippingStatus) {

		switch (shippingStatus) {

			case PACKED:
				order.setOrderStatus(OrderStatus.PACKING);
				break;

			case SHIPPED:
				order.setOrderStatus(OrderStatus.SHIPPED);
				break;

			case OUT_FOR_DELIVERY:
				order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
				break;

			case DELIVERED:
				order.setOrderStatus(OrderStatus.DELIVERED);
				break;

			default:
				break;
		}
	}
	
	private Shipment buildShipment(Order order) {

	    Shipment shipment = new Shipment();

	    shipment.setTrackingNumber(generateTrackingNumber());
	    shipment.setCarrier(assignCarrier());
	    shipment.setShippingStatus(ShippingStatus.CREATED);
	    shipment.setEstimatedDeliveryDate(calculateEstimatedDeliveryDate());

	    order.addShipment(shipment);

	    return shipment;
	}
	
	private void handleShipmentNotification(Shipment shipment, ShippingStatus shippingStatus) {
		
		Order order = shipment.getOrder();

			switch (shippingStatus) {

				case DELIVERED:

					notificationService.createNotification(order.getUser(),
														   order,
														   NotificationType.SHIPMENT_DELIVERED,
														   "Order Delivered",
														   "Your order #" + order.getOrderNumber()
														   + " has been delivered successfully.");
					break;

				default:
					break;
			}
	}

}
