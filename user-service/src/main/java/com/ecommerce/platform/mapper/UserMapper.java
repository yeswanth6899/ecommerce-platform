package com.ecommerce.platform.mapper;


import org.springframework.stereotype.Component;

import com.ecommerce.platform.dto.RegisterUserRequest;
import com.ecommerce.platform.entity.User;

@Component
public class UserMapper {
	


	public User toEntity(RegisterUserRequest request) {
		

		User user = new User();
		
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		return user;
		
		
	}
}
