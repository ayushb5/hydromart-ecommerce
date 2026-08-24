package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.order.OrderResponse;
import com.hydromart.dto.order.PlaceOrderRequest;

public interface OrderService {
	OrderResponse placeOrder(Long userId, PlaceOrderRequest request);
	
	List<OrderResponse> getUserOrders(Long userId);
	
	OrderResponse getOrderById(Long userId, Long orderId);
	
	OrderResponse cancelOrder(Long userId, Long orderId);
}
