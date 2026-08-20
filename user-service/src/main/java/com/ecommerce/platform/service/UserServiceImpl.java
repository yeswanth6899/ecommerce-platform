package com.ecommerce.platform.service;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.platform.dto.ChangePasswordRequest;
import com.ecommerce.platform.dto.LoginRequest;
import com.ecommerce.platform.dto.LoginResponse;
import com.ecommerce.platform.dto.RegisterUserRequest;
import com.ecommerce.platform.dto.UserProfileRequest;
import com.ecommerce.platform.dto.UserProfileResponse;
import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.NotificationType;
import com.ecommerce.platform.entity.Role;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.exception.InvalidCredentialsException;
import com.ecommerce.platform.exception.InvalidPasswordException;
//import com.ecommerce.platform.exception.UnauthorizedException;
import com.ecommerce.platform.exception.UserAlreadyExistsException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.UserMapper;
import com.ecommerce.platform.repository.CartRepository;
import com.ecommerce.platform.repository.UserRepository;
import com.ecommerce.platform.response.ApiResponse;
import com.ecommerce.platform.security.JwtService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final NotificationService notificationService;
	private final CartRepository cartRepository;
	
	
	

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper,
			JwtService jwtService, AuthenticationManager authenticationManager, NotificationService notificationService,
			CartRepository cartRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.notificationService = notificationService;
		this.cartRepository = cartRepository;
	}

	@Override
	public ApiResponse<Void> registerUser(RegisterUserRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			
			throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		User user = userMapper.toEntity(request);
		
		user.setPassword(encodedPassword);
		user.setRole(Role.USER);
		
		User savedUser = userRepository.save(user);
		
		Cart cart = new Cart();
		cart.setUser(savedUser);

		cartRepository.save(cart);
		
		notificationService.createNotification(savedUser,
											   null,
											   NotificationType.WELCOME,
											   "Welcome to E-commerce platform!",
											   "Your Account has been created successfully!");
		
		
		return new ApiResponse<>(
				HttpStatus.CREATED.value(),
				"User Created Successfully.",
				LocalDateTime.now(),
				null);
	}

	@Override
	public ApiResponse<LoginResponse> login(LoginRequest request) {
		
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				
														request.getEmail(),
														request.getPassword()));
		}
		catch(BadCredentialsException ex){
			
			throw new InvalidCredentialsException("Invalid email or password");
		}
		User user = userRepository.findByEmail(request.getEmail())
								  .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
		
	
		String token = jwtService.generateToken(user);
		LoginResponse loginResponse = new LoginResponse(token, "Bearer");
		
		return new ApiResponse<>(
				HttpStatus.OK.value(),
				"User Logged in Successfully",
				LocalDateTime.now(),
				loginResponse
				);
	}

	@Override
	public ApiResponse<UserProfileResponse> getProfile() {
		
		
		User user = getCurrentUser();
		UserProfileResponse response = mapToUserProfileResponse(user);
		
		return new ApiResponse<>(
				HttpStatus.OK.value(),
				"Profile Fetched Successfully",
				LocalDateTime.now(),
				response
				);
	}
	
	@Override
	public ApiResponse<UserProfileResponse> updateProfile(UserProfileRequest request) {
		
		User user = getCurrentUser();
		
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhoneNumber(request.getPhoneNumber());
		
		userRepository.save(user);
		
		UserProfileResponse response = mapToUserProfileResponse(user);
		
		
		return new ApiResponse<>(
				HttpStatus.OK.value(),
				"Profile Updated Successfully",
				LocalDateTime.now(),
				response
				);	
	}

	@Override
	public ApiResponse<String> changePassword(ChangePasswordRequest request) {
		
		User user = getCurrentUser();
		
		if( !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
			
			throw new InvalidCredentialsException("Current Password is incorrect");
		}
		
		
		if(!request.getNewPassword().equals(request.getConfirmPassword())) {
			
			throw new InvalidPasswordException("New password and confirm password do not match");
		}
		
		if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
			
			throw new InvalidPasswordException("New password must be different from the current password.");
		}
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		userRepository.save(user);
		
		return new ApiResponse<>(
				HttpStatus.OK.value(),
				"Password changed Successfully",
				LocalDateTime.now(),
				null //even though the return type is string i have already mentioned the message above so to avoid writing it again i have returned null
				);
	}
	
	
	private User getCurrentUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
//		if(authentication == null || !authentication.isAuthenticated()) {
//			
//			throw new UnauthorizedException("User is not authenticated");
//		}
		
//		Object principal = authentication.getPrincipal();
//		
//		if(!(principal instanceof UserDetails)) {
//			
//			throw new UnauthorizedException("User is not authenticated");
//		}
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		return userRepository.findByEmail(email)
				  .orElseThrow(() -> new UserNotFoundException("User Not Found"));
	}
	
	

	private UserProfileResponse mapToUserProfileResponse(User user) {
		
		UserProfileResponse response = new UserProfileResponse();
		
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setEmail(user.getEmail());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setRole(user.getRole().name());
		
		return response;
	}


	
}
