package com.hydromart.service;

import com.hydromart.dto.cart.AddToCartRequest;
import com.hydromart.dto.cart.CartResponse;

public interface CartService {
	CartResponse addToCart(Long userId, AddToCartRequest request);
	
	CartResponse getCart(Long userId);
	
	CartResponse updateQuantity(Long userId, Long cartItemId, Integer quantity);
	
	void removeItem(Long userId, Long cartItemId);
	
	void clearCart(Long userId);
}
