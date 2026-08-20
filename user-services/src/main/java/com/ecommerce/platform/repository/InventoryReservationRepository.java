package com.ecommerce.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.InventoryReservation;
import com.ecommerce.platform.entity.InventoryReservationStatus;
import com.ecommerce.platform.entity.Order;


public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, Long> {
	
	List<InventoryReservation> findByOrder(Order order);
	
	List<InventoryReservation> findByOrderAndInventoryReservationStatus(
	        Order order,
	        InventoryReservationStatus inventoryReservationStatus);
	
	List<InventoryReservation> findByInventory(Inventory inventory);
	
	List<InventoryReservation>  findByInventoryReservationStatus(InventoryReservationStatus inventoryReservationStatus);

}
