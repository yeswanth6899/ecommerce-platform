package com.ecommerce.platform.service;


import com.ecommerce.platform.dto.ChangePasswordRequest;
import com.ecommerce.platform.dto.LoginRequest;
import com.ecommerce.platform.dto.LoginResponse;
import com.ecommerce.platform.dto.RegisterUserRequest;
import com.ecommerce.platform.dto.UserProfileRequest;
import com.ecommerce.platform.dto.UserProfileResponse;
import com.ecommerce.platform.response.ApiResponse;


public interface UserService {
	
	ApiResponse<Void> registerUser(RegisterUserRequest request);
	
	ApiResponse<LoginResponse> login(LoginRequest request);
	
	ApiResponse<UserProfileResponse> getProfile();
	ApiResponse<UserProfileResponse> updateProfile(UserProfileRequest request);
	ApiResponse<String> changePassword(ChangePasswordRequest request);

}
