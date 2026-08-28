package com.hydromart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
	
	@PostMapping("/add")
	public ResponseEntity<CartResponse> addToCart(Authentication authentication,@RequestBody AddToCartRequest request){
		return ResponseEntity.ok(cartService.addToCart(authentication.getName(), request));
	}
	
	@GetMapping
	public ResponseEntity<CartResponse> getCart(Authentication authentication){
		return ResponseEntity.ok(cartService.getCart(authentication.getName()));
	}
	
	@PutMapping("/items/{cartItemId}")
	public ResponseEntity<CartResponse> updateQuantity(Authentication authentication,@PathVariable Long cartItemId,@RequestParam Integer quantity){
		return ResponseEntity.ok(cartService.updateQuantity(authentication.getName(), cartItemId, quantity));
	}
	
	@DeleteMapping("/items/{cartItemId}")
	public ResponseEntity<String> removeItem(Authentication authentication,@PathVariable Long cartItemId){
		cartService.removeItem(authentication.getName(), cartItemId);
		return ResponseEntity.ok("Item removed successfully");
	}
	
	@DeleteMapping
	public ResponseEntity<String> clearCart(Authentication authentication){
		cartService.clearCart(authentication.getName());
		return ResponseEntity.ok("Cart cleared successfully");
	}
}
