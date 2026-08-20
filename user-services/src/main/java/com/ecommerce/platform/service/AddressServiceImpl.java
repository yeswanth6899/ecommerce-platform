package com.ecommerce.platform.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.AddAddressRequest;
import com.ecommerce.platform.dto.AddressResponse;
import com.ecommerce.platform.dto.UpdateAddressRequest;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.entity.UserAddress;
import com.ecommerce.platform.exception.AddressNotFoundException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.AddressMapper;
import com.ecommerce.platform.repository.UserAddressRepository;
import com.ecommerce.platform.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService{
	
	private final UserRepository userRepository;
	private final UserAddressRepository userAddressRepository;
	private final AddressMapper addressMapper;
	
	

	public AddressServiceImpl(UserRepository userRepository, UserAddressRepository userAddressRepository,
			AddressMapper addressMapper) {
		
		this.userRepository = userRepository;
		this.userAddressRepository = userAddressRepository;
		this.addressMapper = addressMapper;
	}
	
	

	@Override
	@Transactional
	public AddressResponse addAddress(AddAddressRequest request) {
		
		User user = getAuthenticatedUser();
		
		UserAddress address  = addressMapper.toEntity(request);
		
		address.setUser(user);
		
		
		
		if(!userAddressRepository.existsByUser(user)) {
			
			address.setDefaultAddress(true);
			
		}
		
		else if(Boolean.TRUE.equals(request.getDefaultAddress())) {
			
			removeCurrentDefaultAddress(user);
			
			address.setDefaultAddress(true);
		}
		
		UserAddress savedAddress = userAddressRepository.save(address);
		
		return addressMapper.toResponse(savedAddress);
	}

	@Override
	public List<AddressResponse> getAllAddresses() {
		
		User user = getAuthenticatedUser();
		
		return userAddressRepository.findByUser(user)
										.stream()
										.map(addressMapper :: toResponse)
										.toList();
	}

	@Override
	@Transactional
	public AddressResponse updateAddress(Long addressId, UpdateAddressRequest request) {
		
		User user = getAuthenticatedUser();
		
		UserAddress address  = getUserAddressById(addressId, user);
		
		addressMapper.updateEntity(request, address);
		
		return addressMapper.toResponse(address);
	}

	@Override
	@Transactional
	public void deleteAddress(Long addressId) {
		
		User user = getAuthenticatedUser();
		
		UserAddress address = getUserAddressById(addressId, user);
		
		Boolean wasDefault = address.getDefaultAddress();
		
		userAddressRepository.delete(address);
		
		if(!wasDefault) {
			
			return;
		}
		
		
		List<UserAddress> remainingAddresses  = userAddressRepository.findByUser(user);
		
		if(!remainingAddresses.isEmpty()) {
			
			UserAddress newDefaultAddress = remainingAddresses.getFirst();
			
			newDefaultAddress.setDefaultAddress(true);
			
			userAddressRepository.save(newDefaultAddress);
			
		}
			
	}

	@Override
	@Transactional
	public AddressResponse setDefaultAddress(Long addressId) {
		
		User user = getAuthenticatedUser();
		
		UserAddress address = getUserAddressById(addressId, user);
		
		if(address.getDefaultAddress()) {
			
			return addressMapper.toResponse(address);
		}
		
		removeCurrentDefaultAddress(user);
		
		address.setDefaultAddress(true);
		
		return addressMapper.toResponse(address);
	}
	
	
	//Helper Methods
	
	private User getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
								.orElseThrow(() -> new UserNotFoundException("User not found"));
	}
	
	private void removeCurrentDefaultAddress(User user) {
		
		UserAddress currentDefault = userAddressRepository.findByUserAndDefaultAddressTrue(user)
															.orElse(null);
		if(currentDefault!= null) {
			
			currentDefault.setDefaultAddress(false);
			
			userAddressRepository.save(currentDefault);
		}
		
	}
	
	private UserAddress getUserAddressById(Long addressId, User user) {
		
		return userAddressRepository.findByIdAndUser(addressId, user)
											.orElseThrow(() -> new AddressNotFoundException("Address Not found with the id: " + addressId));
	}

}
