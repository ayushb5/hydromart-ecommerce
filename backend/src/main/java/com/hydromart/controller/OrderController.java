package com.hydromart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.order.OrderResponse;
import com.hydromart.dto.order.PlaceOrderRequest;
import com.hydromart.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponse> placeOrder(Authentication authentication,
			@RequestBody @Valid PlaceOrderRequest request) {
		return ResponseEntity.ok(orderService.placeOrder(authentication.getName(), request));
	}

	@GetMapping
	public ResponseEntity<List<OrderResponse>> getUserOrders(Authentication authentication) {
		return ResponseEntity.ok(orderService.getUserOrders(authentication.getName()));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(Authentication authentication, @PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(authentication.getName(), orderId));
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<OrderResponse> cancelOrder(Authentication authentication, @PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.cancelOrder(authentication.getName(), orderId));
	}
}
