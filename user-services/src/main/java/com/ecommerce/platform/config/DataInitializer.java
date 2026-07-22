package com.ecommerce.platform.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.platform.entity.Role;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.repository.UserRepository;

@Configuration
public class DataInitializer {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private static final String ADMIN_EMAIL = "admin@gmail.com";
	private static final String ADMIN_PASSWORD = "Admin@123";
	
	public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Bean
	CommandLineRunner init() {
		
		return args -> {
			
			if(!userRepository.existsByEmail(ADMIN_EMAIL)) {
				
				User admin = new User(); 
				
				admin.setFirstName("System");
				admin.setLastName("Admin");
				admin.setEmail(ADMIN_EMAIL);
				admin.setPhoneNumber("5735874156");
				admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
				admin.setRole(Role.ADMIN);
				
				userRepository.save(admin);
			}
		};
		
	}
	

}
