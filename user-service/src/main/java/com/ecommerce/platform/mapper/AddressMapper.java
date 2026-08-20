package com.ecommerce.platform.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.AddAddressRequest;
import com.ecommerce.platform.dto.AddressResponse;
import com.ecommerce.platform.dto.UpdateAddressRequest;
import com.ecommerce.platform.entity.UserAddress;

@Component
public class AddressMapper {
	
	public UserAddress toEntity(AddAddressRequest request) {
		
		UserAddress address = new UserAddress();
		
		address.setFullName(request.getFullName());
		address.setPhoneNumber(request.getPhoneNumber());
		address.setAddressLine1(request.getAddressLine1());
		address.setAddressLine2(request.getAddressLine2());
		address.setCity(request.getCity());
		address.setState(request.getState());
		address.setPostalCode(request.getPostalCode());
		address.setCountry(request.getCountry());
		address.setDefaultAddress(request.getDefaultAddress());
		
		return address;
	}
	
	public AddressResponse toResponse(UserAddress address) {
		
		AddressResponse response = new AddressResponse();
		
		response.setId(address.getId());
		response.setFullName(address.getFullName());
		response.setPhoneNumber(address.getPhoneNumber());
		response.setAddressLine1(address.getAddressLine1());
		response.setAddressLine2(address.getAddressLine2());
		response.setCity(address.getCity());
		response.setState(address.getState());
		response.setPostalCode(address.getPostalCode());
		response.setCountry(address.getCountry());
		response.setDefaultAddress(address.getDefaultAddress());
		
		return response;
	}
	
	public void updateEntity(UpdateAddressRequest request, UserAddress address) {
		
		address.setFullName(request.getFullName());
		address.setPhoneNumber(request.getPhoneNumber());
		address.setAddressLine1(request.getAddressLine1());
		address.setAddressLine2(request.getAddressLine2());
		address.setCity(request.getCity());
		address.setState(request.getState());
		address.setPostalCode(request.getPostalCode());
		address.setCountry(request.getCountry());
	}

}
