package com.hydromart.dto.order;

import com.hydromart.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
	@NotNull(message="Order status is required")
	private OrderStatus status;
}
