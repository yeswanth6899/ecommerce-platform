package com.ecommerce.platform.service;

import java.util.List;

import com.ecommerce.platform.dto.AddAddressRequest;
import com.ecommerce.platform.dto.AddressResponse;
import com.ecommerce.platform.dto.UpdateAddressRequest;

public interface AddressService {
	
	AddressResponse addAddress(AddAddressRequest request);
	
	List<AddressResponse> getAllAddresses(); 
	
	AddressResponse updateAddress(Long addressId, UpdateAddressRequest request);
	
	void deleteAddress(Long addressId);
	
	AddressResponse setDefaultAddress(Long addressId);

}
