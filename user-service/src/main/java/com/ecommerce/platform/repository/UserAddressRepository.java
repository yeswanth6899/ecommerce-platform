package com.ecommerce.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
	
	List<UserAddress> findByUser(User user);
	
	Optional<UserAddress> findByIdAndUser(Long id, User user);
	
	Optional<UserAddress> findByUserAndDefaultAddressTrue(User user);
	
	boolean existsByUser(User user);

}
