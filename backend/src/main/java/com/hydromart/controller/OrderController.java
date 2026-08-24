package com.hydromart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

	@PostMapping("/{userId}")
	public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long userId,
			@RequestBody @Valid PlaceOrderRequest request) {
		return ResponseEntity.ok(orderService.placeOrder(userId, request));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
		return ResponseEntity.ok(orderService.getUserOrders(userId));
	}

	@GetMapping("/{userId}/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(userId, orderId));
	}

	@PutMapping("/{userId}/{orderId}/cancel")
	public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long userId, @PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.cancelOrder(userId, orderId));
	}
}
