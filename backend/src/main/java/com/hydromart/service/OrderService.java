package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.order.OrderResponse;
import com.hydromart.dto.order.PlaceOrderRequest;
import com.hydromart.dto.order.UpdateOrderStatusRequest;

public interface OrderService {
	OrderResponse placeOrder(String email, PlaceOrderRequest request);
	
	List<OrderResponse> getUserOrders(String email);
	
	OrderResponse getOrderById(String email, Long orderId);
	
	OrderResponse cancelOrder(String email, Long orderId);
	
//	Admin APIs
	
	List<OrderResponse> getAllOrders();
	
	OrderResponse getOrderById(Long orderId);
	
	OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
}
