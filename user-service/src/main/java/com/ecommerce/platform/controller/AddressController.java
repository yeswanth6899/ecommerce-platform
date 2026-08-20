package com.ecommerce.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.AddAddressRequest;
import com.ecommerce.platform.dto.AddressResponse;
import com.ecommerce.platform.dto.UpdateAddressRequest;
import com.ecommerce.platform.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
	
	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		
		this.addressService = addressService;
	}
	
	@PostMapping
	public ResponseEntity<AddressResponse> addAddress(@Valid @RequestBody AddAddressRequest request){
		
		AddressResponse response = addressService.addAddress(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<AddressResponse>> getAllAddresses(){
		
		return ResponseEntity.ok(addressService.getAllAddresses());
	}
	
	@PutMapping("/{addressId}")
	public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId,@Valid @RequestBody UpdateAddressRequest request){
		
		return ResponseEntity.ok(addressService.updateAddress(addressId, request));
		
	}
	
	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId){
		
		addressService.deleteAddress(addressId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{addressId}/default")
	public ResponseEntity<AddressResponse> setDefaultAddress(@PathVariable Long addressId){
		
		return ResponseEntity.ok(addressService.setDefaultAddress(addressId));
	}

}
