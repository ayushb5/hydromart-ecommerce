package com.hydromart.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hydromart.dto.cart.AddToCartRequest;
import com.hydromart.dto.cart.CartItemResponse;
import com.hydromart.dto.cart.CartResponse;
import com.hydromart.entity.Cart;
import com.hydromart.entity.CartItem;
import com.hydromart.entity.Product;
import com.hydromart.entity.User;
import com.hydromart.exception.CartItemNotFoundException;
import com.hydromart.exception.CartNotFoundException;
import com.hydromart.exception.ProductNotFoundException;
import com.hydromart.exception.UserNotFoundException;
import com.hydromart.repository.CartItemRepository;
import com.hydromart.repository.CartRepository;
import com.hydromart.repository.ProductRepository;
import com.hydromart.repository.UserRepository;
import com.hydromart.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
		CartItemResponse response = new CartItemResponse();

		response.setProductId(cartItem.getProduct().getId());
		response.setProductName(cartItem.getProduct().getName());
		response.setImageUrl(cartItem.getProduct().getImageUrl());
		response.setPrice(cartItem.getProduct().getPrice());
		response.setQuantity(cartItem.getQuantity());

		response.setSubtotal(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

		return response;
	}

	private CartResponse mapToCartResponse(Cart cart) {
		CartResponse response = new CartResponse();

		response.setCartId(cart.getId());

		List<CartItemResponse> items = cart.getCartItems().stream().map(cartItem -> mapToCartItemResponse(cartItem))
				.toList();

		response.setItems(items);

		BigDecimal totalAmount = items.stream().map(CartItemResponse::getSubtotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		response.setTotalAmount(totalAmount);

		return response;
	}

	@Override
	public CartResponse addToCart(Long userId, AddToCartRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Product product = productRepository.findById(request.getProductId())
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		if (!product.isActive()) {
			throw new ProductNotFoundException("Product is not available");
		}
		if (request.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUser(user);
			newCart.setCreatedAt(LocalDateTime.now());
			newCart.setUpdatedAt(LocalDateTime.now());

			return cartRepository.save(newCart);
		});

		Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			
			int newQuantity=cartItem.getQuantity()+request.getQuantity();
			if(newQuantity>product.getStockQuantity()) {
				throw new IllegalArgumentException("Insufficient stock available");
			}
			cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
			cartItemRepository.save(cartItem);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());

			cartItemRepository.save(cartItem);
		}

		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);

		return mapToCartResponse(cart);
	}

	@Override
	public CartResponse getCart(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));

		return mapToCartResponse(cart);
	}

	@Override
	public CartResponse updateQuantity(Long userId, Long cartItemId, Integer quantity) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));

		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

		if (!cartItem.getCart().getId().equals(cart.getId())) {
			throw new CartItemNotFoundException("Cart item not found");
		}

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		
		if(quantity>cartItem.getProduct().getStockQuantity()) {
			throw new IllegalArgumentException("Insufficient stock available");
		}
		
		cartItem.setQuantity(quantity);
		cartItemRepository.save(cartItem);
		
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);
		
		return mapToCartResponse(cart);
	}

	@Override
	public void removeItem(Long userId, Long cartItemId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));

		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

		if (!cartItem.getCart().getId().equals(cart.getId())) {
			throw new CartItemNotFoundException("Cart item not found");
		}
		
		cartItemRepository.delete(cartItem);
		
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));

		cartItemRepository.deleteByCart(cart);
		
		cart.setUpdatedAt(LocalDateTime.now());
		
		cartRepository.save(cart);
	}

}
