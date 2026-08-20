package com.ecommerce.platform.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



@Entity
@Table(name = "users")
public class User extends BaseEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(nullable = false)
		private String firstName;
		
		@Column(nullable = false)
		private String lastName;
		
		@Column(nullable = false, unique = true)
		private String email;
		
		@Column(nullable = false)
		private String password;
		
		@Enumerated(EnumType.STRING)
		private Role role;
		
		@NotBlank(message = "Phone number is Required")
		@Pattern(regexp = "^[0-9]{10}$",
				 message = "Phone number must be exactly 10 digits")
		private String phoneNumber;
		
		@OneToMany(
				mappedBy = "user",
				cascade = CascadeType.ALL,
				orphanRemoval = true
				)
		private List<UserAddress> userAddresses = new ArrayList<>();
		
		
		public User() {
			
		}

		

		public User(String firstName, String lastName, String email, String password, Role role, String phoneNumber) {
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			this.role = role;
			this.phoneNumber = phoneNumber;
		}



		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFirstName() {
			return firstName;
		}


		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}


		public String getLastName() {
			return lastName;
		}


		public void setLastName(String lastName) {
			this.lastName = lastName;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public Role getRole() {
			return role;
		}


		public void setRole(Role role) {
			this.role = role;
		}


		public String getPhoneNumber() {
			return phoneNumber;
		}


		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		
		public List<UserAddress> getUserAddresses() {
			return userAddresses;
		}

		public void setUserAddresses(List<UserAddress> userAddresses) {
			this.userAddresses = userAddresses;
		}

		
		//Helper Methods

		public void addAddress(UserAddress address) {

		    userAddresses.add(address);

		    address.setUser(this);
		}
		
		public void removeAddress(UserAddress address) {

		    userAddresses.remove(address);

		    address.setUser(null);
		}
}
