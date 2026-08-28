package com.hydromart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.order.OrderResponse;
import com.hydromart.dto.order.UpdateOrderStatusRequest;
import com.hydromart.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId){
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@PutMapping("/{orderId}/status")
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderStatusRequest request){
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request));
	}
}
