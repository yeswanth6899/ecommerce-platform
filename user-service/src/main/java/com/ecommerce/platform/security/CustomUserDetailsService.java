package com.ecommerce.platform.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username)
								  .orElseThrow(() -> new UsernameNotFoundException("User not found with the email " + username));
		
		
		return org.springframework.security.core.userdetails.User.builder()
									.username(user.getEmail())
									.password(user.getPassword())
									.roles(user.getRole().name())
									.build();
	}

}
