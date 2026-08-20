package com.ecommerce.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.Order;
import com.ecommerce.platform.entity.Shipment;
import com.ecommerce.platform.entity.ShippingStatus;
import com.ecommerce.platform.entity.User;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
	
	Optional<Shipment> findByTrackingNumberAndOrderUser(String trackingNumber, User user);
	
	Optional<Shipment> findByTrackingNumber(String trackingNumber);
	
	List<Shipment> findByOrderOrderByCreatedAtDesc(Order order);
	
	List<Shipment> findByOrderUserOrderByCreatedAtDesc(User user);
	
	List<Shipment> findByShippingStatusOrderByCreatedAtDesc(ShippingStatus shippingStatus);

}
