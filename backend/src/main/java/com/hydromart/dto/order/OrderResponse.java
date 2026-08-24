package com.hydromart.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.hydromart.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
	private Long orderId;
	private List<OrderItemResponse> items;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private String shippingAddress;
	private String paymentMethod;
	private LocalDateTime createdAt;
}
