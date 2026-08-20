package com.ecommerce.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.InventoryCreateRequest;
import com.ecommerce.platform.dto.InventoryResponse;
import com.ecommerce.platform.dto.InventoryUpdateRequest;
import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.InventoryReservation;
import com.ecommerce.platform.entity.InventoryReservationStatus;
import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.OrderItem;
import com.ecommerce.platform.entity.Product;
import com.ecommerce.platform.exception.InsufficientStockException;
import com.ecommerce.platform.exception.InvalidInventoryReservationStateException;
import com.ecommerce.platform.exception.InvalidInventoryStateException;
import com.ecommerce.platform.exception.InventoryAlreadyExistsException;
import com.ecommerce.platform.exception.InventoryNotFoundException;
import com.ecommerce.platform.exception.InventoryReservationNotFoundException;
import com.ecommerce.platform.exception.ProductNotFoundException;
import com.ecommerce.platform.mapper.InventoryMapper;
import com.ecommerce.platform.repository.InventoryRepository;
import com.ecommerce.platform.repository.InventoryReservationRepository;
import com.ecommerce.platform.repository.ProductRepository;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{
	
	private final InventoryRepository inventoryRepository;
	private final ProductRepository productRepository;
	private final InventoryMapper inventoryMapper;
	private final InventoryReservationRepository inventoryReservationRepository;
	
	

	public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository,
			InventoryMapper inventoryMapper, InventoryReservationRepository inventoryReservationRepository) {
		
		this.inventoryRepository = inventoryRepository;
		this.productRepository = productRepository;
		this.inventoryMapper = inventoryMapper;
		this.inventoryReservationRepository = inventoryReservationRepository;
	}


	@Override
    public InventoryResponse createInventory(InventoryCreateRequest request) {

        Long productId = request.getProductId();

        Product product = fetchProductById(productId);

        if (inventoryRepository.existsByProductId(productId)) {
            throw new InventoryAlreadyExistsException(
                    "Inventory already exists for product id: " + productId);
        }

        Inventory inventory = inventoryMapper.toEntity(request, product);

        Inventory savedInventory = inventoryRepository.save(inventory);

        // Fetch again with JOIN FETCH so Product is initialized
        Inventory inventoryWithProduct = fetchByInventoryId(savedInventory.getId());

        return inventoryMapper.toResponse(inventoryWithProduct);
    }
	

	@Override
	public InventoryResponse getInventoryById(Long inventoryId) {
		
		Inventory inventory = fetchByInventoryId(inventoryId);
		
		return inventoryMapper.toResponse(inventory);
		
	}

	@Override
	public List<InventoryResponse> getAllInventories() {
		
		List<Inventory> inventories = inventoryRepository.findAllWithProduct();

		return inventories.stream()
		        .map(inventoryMapper::toResponse)
		        .toList();
	}

	@Override
	public InventoryResponse updateInventory(Long inventoryId,
	                                         InventoryUpdateRequest request) {

	    Inventory inventory = fetchByInventoryId(inventoryId);

	    validateInventoryUpdate(inventory, request);

	    inventoryMapper.updateEntity(inventory, request);

	    return inventoryMapper.toResponse(inventory);
	}
	
	@Override
	public void reserveStock(Order order) {
		
		for(OrderItem orderItem : order.getOrderItems()) {
			
			Inventory inventory = getInventory(orderItem.getProduct());
			
			validateAvailableStock(inventory, orderItem.getQuantity());
			
			updateInventoryForReservation(inventory, orderItem.getQuantity());
			
			InventoryReservation reservation = buildReservation(inventory, order, orderItem.getQuantity());
			
			inventoryReservationRepository.save(reservation);
		}
		
	}

	@Override
	public void confirmReservation(Order order) {
		
		List<InventoryReservation> reservations =getReservedReservations(order);
		
		for(InventoryReservation reservation : reservations) {
			
			validateReservationStatus(
		            reservation,
		            InventoryReservationStatus.RESERVED);
			
			updateInventoryForConfirmation(reservation.getInventory(), reservation.getQuantity());
			
			reservation.setInventoryReservationStatus(InventoryReservationStatus.CONFIRMED);
		}
		
	}

	@Override
	public void releaseReservation(Order order) {
		
		List<InventoryReservation> reservations =getReservedReservations(order);
		
		for(InventoryReservation reservation : reservations) {
			
			validateReservationStatus(
		            reservation,
		            InventoryReservationStatus.RESERVED);
			
			updateInventoryForRelease(reservation.getInventory(), reservation.getQuantity());
			
			reservation.setInventoryReservationStatus(InventoryReservationStatus.RELEASED);
		}
		
	}
	
	//Helper Methods
	private Inventory fetchByInventoryId(Long inventoryId) {

	    return inventoryRepository.findByIdWithProduct(inventoryId)
	            .orElseThrow(() -> new InventoryNotFoundException(
	                    "Inventory not found with id: " + inventoryId));
	}
	
	private Inventory getInventory(Product product) {
		
		return inventoryRepository.findByProduct(product)
									.orElseThrow(() -> new InventoryNotFoundException(
											"Inventory not found with the product: " + product.getName()));
	}
	
	private void validateAvailableStock(Inventory inventory, Integer requestedQuantity) {
		
		if(inventory.getAvailableStock() < requestedQuantity) {
			
			throw new InsufficientStockException("Insufficient stock for the product: " + inventory.getProduct().getName());
		}
	}
	
	private void updateInventoryForReservation(Inventory inventory, Integer quantity) {
		
		inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
		
		inventory.setReservedStock(inventory.getReservedStock() + quantity);
	}
	
	private InventoryReservation buildReservation(Inventory inventory, Order order, Integer quantity) {
		
		InventoryReservation reservation = new InventoryReservation();
		
		reservation.setInventory(inventory);
		reservation.setOrder(order);
		reservation.setQuantity(quantity);
		reservation.setInventoryReservationStatus(InventoryReservationStatus.RESERVED);
		
		return reservation;
	}
	
	private void updateInventoryForConfirmation(Inventory inventory, Integer quantity) { 
		
		inventory.setReservedStock(inventory.getReservedStock() - quantity);
	}
	
	private void updateInventoryForRelease(Inventory inventory, Integer quantity) {
		
		inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
		
		inventory.setReservedStock(inventory.getReservedStock() - quantity);
		
	}
	
	private void validateReservationStatus(InventoryReservation reservation, InventoryReservationStatus expectedStatus) {
		
		if(reservation.getInventoryReservationStatus() != expectedStatus) {
			
			throw new InvalidInventoryReservationStateException(
							"Inventory reservation is in "
	                        + reservation.getInventoryReservationStatus()
	                        + " state. Expected "
	                        + expectedStatus + ".");
		}
	}
	
	private List<InventoryReservation> getReservedReservations(Order order) {

	    List<InventoryReservation> reservations =
	            inventoryReservationRepository
	                    .findByOrderAndInventoryReservationStatus(
	                            order,
	                            InventoryReservationStatus.RESERVED);

	    if (reservations.isEmpty()) {

	        throw new InventoryReservationNotFoundException(
	                "No RESERVED inventory reservations found for order: "
	                        + order.getOrderNumber());
	    }

	    return reservations;
	}
	
	private Product fetchProductById(Long productId) {

	    return productRepository.findById(productId)
	            .orElseThrow(() ->
	                    new ProductNotFoundException(
	                            "Product not found with id: " + productId));
	}
	
	private void validateInventoryUpdate(Inventory inventory,
            InventoryUpdateRequest request) {

		if (request.getAvailableStock() < inventory.getReservedStock()) {

			throw new InvalidInventoryStateException(
					"Available stock cannot be less than reserved stock.");
		}
	}
}
