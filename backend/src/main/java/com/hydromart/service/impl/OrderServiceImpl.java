package com.hydromart.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hydromart.dto.order.OrderItemResponse;
import com.hydromart.dto.order.OrderResponse;
import com.hydromart.dto.order.PlaceOrderRequest;
import com.hydromart.entity.Cart;
import com.hydromart.entity.CartItem;
import com.hydromart.entity.Order;
import com.hydromart.entity.OrderItem;
import com.hydromart.entity.Product;
import com.hydromart.entity.User;
import com.hydromart.enums.OrderStatus;
import com.hydromart.exception.CartNotFoundException;
import com.hydromart.exception.OrderNotFoundException;
import com.hydromart.repository.CartItemRepository;
import com.hydromart.repository.CartRepository;
import com.hydromart.repository.OrderRepository;
import com.hydromart.repository.ProductRepository;
import com.hydromart.repository.UserRepository;
import com.hydromart.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
		OrderItemResponse response = new OrderItemResponse();

		response.setProductId(orderItem.getProduct().getId());
		response.setProductName(orderItem.getProduct().getName());
		response.setQuantity(orderItem.getQuantity());
		response.setPrice(orderItem.getPrice());
		response.setSubtotal(orderItem.getSubtotal());

		return response;
	}

	private OrderResponse mapToOrderResponse(Order order) {
		OrderResponse response = new OrderResponse();
		response.setOrderId(order.getId());

		List<OrderItemResponse> items = order.getOrderItems().stream()
				.map(orderItem -> mapToOrderItemResponse(orderItem)).toList();

		response.setItems(items);
		response.setTotalAmount(order.getTotalAmount());
		response.setShippingAddress(order.getShippingAddress());
		response.setPaymentMethod(order.getPaymentMethod());
		response.setStatus(order.getStatus());
		response.setCreatedAt(order.getCreatedAt());

		return response;
	}

	@Override
	public OrderResponse placeOrder(Long userId, PlaceOrderRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));

		if (cart.getCartItems().isEmpty()) {
			throw new IllegalArgumentException("Cart is empty");
		}

		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.PENDING);
		order.setShippingAddress(request.getShippingAddress());
		order.setPaymentMethod(request.getPaymentMethod());

		order.setCreatedAt(LocalDateTime.now());
		order.setUpdatedAt(LocalDateTime.now());

		BigDecimal totalAmount = BigDecimal.ZERO;

		for (CartItem cartItem : cart.getCartItems()) {
			Product product = cartItem.getProduct();

			if (cartItem.getQuantity() > product.getStockQuantity()) {
				throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
			}

			BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

			OrderItem orderItem = new OrderItem();

			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(cartItem.getQuantity());

			orderItem.setPrice(product.getPrice());

			orderItem.setSubtotal(subtotal);

			order.getOrderItems().add(orderItem);

			totalAmount = totalAmount.add(subtotal);

			product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

			productRepository.save(product);
		}

		order.setTotalAmount(totalAmount);

		Order savedOrder = orderRepository.save(order);

		cart.getCartItems().clear();
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);

		return mapToOrderResponse(savedOrder);
	}

	@Override
	public List<OrderResponse> getUserOrders(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return orderRepository.findByUser(user).stream().map(order -> mapToOrderResponse(order)).toList();

	}

	@Override
	public OrderResponse getOrderById(Long userId, Long orderId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found"));

		if (!order.getUser().getId().equals(user.getId())) {
			throw new OrderNotFoundException("Order not found");
		}

		return mapToOrderResponse(order);
	}

	@Override
	public OrderResponse cancelOrder(Long userId, Long orderId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found"));

		if (!order.getUser().getId().equals(user.getId())) {
			throw new OrderNotFoundException("Order not found");
		}

		if (order.getStatus() != OrderStatus.PENDING) {
			throw new IllegalStateException("Only pending orders can be cancelled");
		}

		for (OrderItem item : order.getOrderItems()) {
			Product product = item.getProduct();

			product.setStockQuantity(product.getStockQuantity() + item.getQuantity());

			productRepository.save(product);
		}

		order.setStatus(OrderStatus.CANCELLED);
		order.setUpdatedAt(LocalDateTime.now());
		Order savedOrder = orderRepository.save(order);

		return mapToOrderResponse(savedOrder);
	}

}
