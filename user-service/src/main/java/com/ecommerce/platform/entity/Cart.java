package com.ecommerce.platform.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@OneToMany(mappedBy = "cart" , 
			   cascade = CascadeType.ALL , 
			   orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();

	public Cart() {
		
	}

	public Cart(User user, List<CartItem> cartItems) {
		
		this.user = user;
		this.cartItems = (cartItems != null)
		        ? cartItems
		        : new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	//Helper Methods
	
	public void addCartItem(CartItem cartItem) {

	    if (!cartItems.contains(cartItem)) {

	        cartItems.add(cartItem);

	        cartItem.setCart(this);

	    }

	}

	public void removeCartItem(CartItem cartItem) {
	    cartItems.remove(cartItem);
	    cartItem.setCart(null);
	}
	
	public void clearCartItems() {

	    for (CartItem item : new ArrayList<>(cartItems)) {
	        removeCartItem(item);
	    }
	}
}
