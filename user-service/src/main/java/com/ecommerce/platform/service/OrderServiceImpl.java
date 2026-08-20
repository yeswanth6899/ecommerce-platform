package com.ecommerce.platform.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.OrderResponse;
import com.ecommerce.platform.dto.PlaceOrderRequest;
import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.CartItem;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.OrderItem;
import com.ecommerce.platform.entity.OrderStatus;
import com.ecommerce.platform.entity.PaymentStatus;
import com.ecommerce.platform.entity.Product;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.entity.UserAddress;
import com.ecommerce.platform.exception.AddressNotFoundException;
import com.ecommerce.platform.exception.CartNotFoundException;
import com.ecommerce.platform.exception.EmptyCartException;
import com.ecommerce.platform.exception.OrderNotFoundException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.OrderMapper;
import com.ecommerce.platform.repository.CartRepository;
import com.ecommerce.platform.repository.OrderRepository;
import com.ecommerce.platform.repository.UserAddressRepository;
import com.ecommerce.platform.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final UserAddressRepository userAddressRepository;
	private final InventoryService inventoryService;
	private final OrderMapper orderMapper;
	
	private static final BigDecimal TAX_RATE = new BigDecimal("0.08");
	private static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("100.00");
	private static final BigDecimal SHIPPING_COST = new BigDecimal("10.00");
	
	

	public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
			CartRepository cartRepository, UserAddressRepository userAddressRepository,
			InventoryService inventoryService, OrderMapper orderMapper) {
	
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.userAddressRepository = userAddressRepository;
		this.inventoryService = inventoryService;
		this.orderMapper = orderMapper;
	}

	@Override
	@Transactional
	public OrderResponse placeOrder(PlaceOrderRequest request) {
		
		User user = getAuthenticatedUser();
		Cart cart = getUserCart(user);
		
		validateCart(cart);
		
		UserAddress address = getShippingAddress(user, request.getShippingAddressId());
		
//		validateInventory(cart);
		
		Order order = createOrder(user, address, request);
		
		createOrderItems(order, cart);
		
		calculateOrderTotals(order);
		
		Order savedOrder = orderRepository.save(order);
		
		inventoryService.reserveStock(savedOrder);
		
		//clearCart(cart);
		
		cart.clearCartItems();
		
		return orderMapper.toResponse(savedOrder);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponse> getMyOrders() {
		
		User user = getAuthenticatedUser();
		
		return orderRepository.findByUserOrderByCreatedAtDesc(user)
									.stream()
									.map(orderMapper :: toResponse)
									.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse getOrderByOrderNumber(String orderNumber) {
		
		User user = getAuthenticatedUser();
		
		Order order = orderRepository.findByOrderNumberAndUser(orderNumber, user)
										.orElseThrow(() -> new OrderNotFoundException("Order not found with order number: " + orderNumber));
		
		return orderMapper.toResponse(order);
	}
	
	
	
	//Helper Methods
	
	private User getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
					.orElseThrow(() -> new UserNotFoundException("User not found"));
		
	}
	
	private Cart getUserCart(User user) {
		
		return cartRepository.findByUser(user)
								.orElseThrow(() -> new CartNotFoundException("Cart not found"));
	}
	
	private void validateCart(Cart cart) {
		
		if(cart.getCartItems().isEmpty()) {
			
			throw new EmptyCartException("Cannot place order with an empty cart");
		}
	}
	
	private UserAddress getShippingAddress(User user, Long addressId) {
		
		return userAddressRepository.findByIdAndUser(addressId, user)
										.orElseThrow(() -> 
														new AddressNotFoundException("Address not found with id: " + addressId));
	}
	
//	private Inventory getInventory(Product product) {
//		
//		return inventoryRepository.findByProduct(product)
//									.orElseThrow(() -> 
//													new InventoryNotFoundException("Inventory Not found for product id: " + product.getId()));
//	}
	
//	private void validateInventory(Cart cart) {
//		
//		for(CartItem cartItem : cart.getCartItems()) {
//			
//			Product product = cartItem.getProduct();
//			
//			Inventory inventory = getInventory(product);
//			
//			if(cartItem.getQuantity() > inventory.getAvailableStock()) {
//				
//				throw new InsufficientStockException( "Requested quantity for "
//															+ product.getName()
//															+ " exceeds the available stock ("
//															+ inventory.getAvailableStock() + ").");
//			}
//		}	
//	}
	
	
	private String generateOrderNumber() {
		
		String orderDate = LocalDate.now()
								.format(DateTimeFormatter.BASIC_ISO_DATE);
		
		String randomSuffix = UUID.randomUUID()
									.toString()
									.substring(0, 6)
									.toUpperCase();
		
		return "ORD-" + orderDate + "-" + randomSuffix;
	}
	
	
	private Order createOrder(User user, UserAddress address, PlaceOrderRequest request) {
		
		Order order = new Order();
		
		order.setOrderNumber(generateOrderNumber());
		order.setUser(user);
		order.setOrderStatus(OrderStatus.CREATED);
		order.setPaymentStatus(PaymentStatus.PENDING);
		order.setPaymentMethod(request.getPaymentMethod());
		
		order.setShippingFullName(address.getFullName());
		order.setShippingPhoneNumber(address.getPhoneNumber());
		order.setShippingAddressLine1(address.getAddressLine1());
		order.setShippingAddressLine2(address.getAddressLine2());
		order.setShippingCity(address.getCity());
		order.setShippingState(address.getState());
		order.setShippingPostalCode(address.getPostalCode());
		order.setShippingCountry(address.getCountry());
		
		return order;
		
	}
	
	private void createOrderItems(Order order, Cart cart) {
		
		for(CartItem cartItem : cart.getCartItems()) {
			
			Product product = cartItem.getProduct();
			BigDecimal unitPrice = product.getPrice();
			Integer quantity = cartItem.getQuantity();
			BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
			
			OrderItem orderItem = new OrderItem(order, product, product.getName(), product.getImageUrl(), unitPrice,
													quantity, subtotal);
			order.addOrderItem(orderItem);
		}
	}
	
	private void calculateOrderTotals(Order order) {
		
		BigDecimal subtotal  = BigDecimal.ZERO;
		int totalQuantity = 0;
		
		for(OrderItem item : order.getOrderItems()) {
			
			subtotal = subtotal.add(item.getSubtotal());
			totalQuantity += item.getQuantity();
			
		}
		
		BigDecimal taxAmount = subtotal.multiply(TAX_RATE)
										.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal shippingCost  = subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >=0 ? BigDecimal.ZERO : SHIPPING_COST;
		
		BigDecimal discountAmount = BigDecimal.ZERO;
		
		BigDecimal totalAmount = subtotal
										 .add(taxAmount)
										 .add(shippingCost)
										 .subtract(discountAmount);
		
		order.setSubtotal(subtotal);
		order.setTaxAmount(taxAmount);
		order.setShippingCost(shippingCost);
		order.setDiscountAmount(discountAmount);
		order.setTotalAmount(totalAmount);
		order.setTotalQuantity(totalQuantity);
	}
	
//	private void clearCart(Cart cart) {
//		
//		cart.clearCartItems();
//		
//	}
}
