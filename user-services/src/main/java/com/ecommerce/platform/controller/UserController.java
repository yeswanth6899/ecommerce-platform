package com.ecommerce.platform.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.platform.dto.ChangePasswordRequest;
import com.ecommerce.platform.dto.LoginRequest;
import com.ecommerce.platform.dto.LoginResponse;
import com.ecommerce.platform.dto.RegisterUserRequest;
import com.ecommerce.platform.dto.UserProfileRequest;
import com.ecommerce.platform.dto.UserProfileResponse;
import com.ecommerce.platform.response.ApiResponse;
import com.ecommerce.platform.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		
		this.userService = userService;
	}

	@PostMapping("/register")
	public ApiResponse<Void> registerUser(@Valid @RequestBody RegisterUserRequest request){
	
		return userService.registerUser(request);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

	    ApiResponse<LoginResponse> response = userService.login(request);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(){
		
		ApiResponse<UserProfileResponse> response = userService.getProfile();
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(@Valid @RequestBody UserProfileRequest request){
		
		ApiResponse<UserProfileResponse> response = userService.updateProfile(request);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest request){
		
		ApiResponse<String> response = userService.changePassword(request);
		
		return ResponseEntity.ok(response);
		
	}
}
