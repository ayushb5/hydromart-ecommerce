package com.hydromart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.cart.AddToCartRequest;
import com.hydromart.dto.cart.CartResponse;
import com.hydromart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	
	@PostMapping("/{userId}/add")
	public ResponseEntity<CartResponse> addToCart(@PathVariable Long userId,@RequestBody AddToCartRequest request){
		return ResponseEntity.ok(cartService.addToCart(userId, request));
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartResponse> getCart(@PathVariable Long userId){
		return ResponseEntity.ok(cartService.getCart(userId));
	}
	
	@PutMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<CartResponse> updateQuantity(@PathVariable Long userId,@PathVariable Long cartItemId,@RequestParam Integer quantity){
		return ResponseEntity.ok(cartService.updateQuantity(userId, cartItemId, quantity));
	}
	
	@DeleteMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<String> removeItem(@PathVariable Long userId,@PathVariable Long cartItemId){
		cartService.removeItem(userId, cartItemId);
		return ResponseEntity.ok("Item removed successfully");
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> clearCart(@PathVariable Long userId){
		cartService.clearCart(userId);
		return ResponseEntity.ok("Cart cleared successfully");
	}
}
