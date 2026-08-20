package com.ecommerce.platform.config;

import com.ecommerce.platform.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.platform.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 private final CustomUserDetailsService userDetailsService;

	    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.userDetailsService = userDetailsService;
			this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }

	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    AuthenticationProvider authenticationProvider() {

	    DaoAuthenticationProvider authenticationProvider =  new DaoAuthenticationProvider(userDetailsService);

	    authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
		
	}
	    
	@Bean
	AuthenticationManager authenticatioManager(AuthenticationConfiguration configuration) throws Exception{
		
		return configuration.getAuthenticationManager();
		
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthenticationFilter,
	                UsernamePasswordAuthenticationFilter.class)
	        .authorizeHttpRequests(auth -> auth

	            // Public APIs
	            .requestMatchers("/api/v1/users/register",
	                             "/api/v1/users/login")
	            .permitAll()

	            // Everything else
	            .anyRequest()
	            .authenticated());

	    return http.build();
	}
		
}
