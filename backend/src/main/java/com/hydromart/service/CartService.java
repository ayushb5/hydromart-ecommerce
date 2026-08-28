package com.hydromart.service;

import com.hydromart.dto.cart.AddToCartRequest;
import com.hydromart.dto.cart.CartResponse;

public interface CartService {
	CartResponse addToCart(String email, AddToCartRequest request);
	
	CartResponse getCart(String email);
	
	CartResponse updateQuantity(String email, Long cartItemId, Integer quantity);
	
	void removeItem(String email, Long cartItemId);
	
	void clearCart(String email);
}
