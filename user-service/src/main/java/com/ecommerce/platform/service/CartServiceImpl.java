package com.ecommerce.platform.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.AddToCartRequest;
import com.ecommerce.platform.dto.CartResponse;
import com.ecommerce.platform.dto.UpdateCartItemRequest;
import com.ecommerce.platform.entity.Cart;
import com.ecommerce.platform.entity.CartItem;
import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.Product;
import com.ecommerce.platform.entity.User;
import com.ecommerce.platform.exception.CartItemNotFoundException;
import com.ecommerce.platform.exception.CartNotFoundException;
import com.ecommerce.platform.exception.InsufficientStockException;
import com.ecommerce.platform.exception.InventoryNotFoundException;
import com.ecommerce.platform.exception.ProductNotFoundException;
import com.ecommerce.platform.exception.UserNotFoundException;
import com.ecommerce.platform.mapper.CartMapper;
import com.ecommerce.platform.repository.CartItemRepository;
import com.ecommerce.platform.repository.CartRepository;
import com.ecommerce.platform.repository.InventoryRepository;
import com.ecommerce.platform.repository.ProductRepository;
import com.ecommerce.platform.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final InventoryRepository inventoryRepository;

    public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository,CartRepository cartRepository,
                           CartItemRepository cartItemRepository,CartMapper cartMapper,
                           InventoryRepository inventoryRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        User user = getAuthenticatedUser();

        Cart cart = cartRepository.findByUser(user)
                					.orElseGet(() -> createCart(user));

        Product product = getProduct(request.getProductId());

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

            cartItemRepository.save(cartItem);

        } 
        
        else {

            CartItem cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());

            cartItemRepository.save(cartItem);
        }

        return cartMapper.toCartResponse(getUserCart());
    }

    @Override
    public CartResponse getCart() {

        return cartMapper.toCartResponse(getUserCart());
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(UpdateCartItemRequest request) {

        Cart cart = getUserCart();

        CartItem cartItem = getCartItem(cart, request.getCartItemId());

        Inventory inventory = getInventory(cartItem.getProduct());

        if (request.getQuantity() == 0) {

            cartItemRepository.delete(cartItem);

            return cartMapper.toCartResponse(getUserCart());
        }

        if (request.getQuantity() > inventory.getAvailableStock()) {

            throw new InsufficientStockException("Requested quantity exceeds available stock");
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeCartItem(Long cartItemId) {

        Cart cart = getUserCart();

        CartItem cartItem = getCartItem(cart, cartItemId);

        cartItemRepository.delete(cartItem);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse clearCart() {

        Cart cart = getUserCart();

        cartItemRepository.deleteByCart(cart);

        return cartMapper.toCartResponse(getUserCart());
    }

   
    // Helper Methods
   

    private Cart createCart(User user) {

        Cart cart = new Cart();

        cart.setUser(user);

        return cartRepository.save(cart);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    private Cart getUserCart() {

        User user = getAuthenticatedUser();

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found"));
    }

    private Product getProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));
    }

    private CartItem getCartItem(Cart cart, Long cartItemId) {

        return cartItemRepository.findByIdAndCart(cartItemId, cart)
                .orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart item not found with id: " + cartItemId));
    }

    private Inventory getInventory(Product product) {

        return inventoryRepository.findByProduct(product)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found"));
    }
}