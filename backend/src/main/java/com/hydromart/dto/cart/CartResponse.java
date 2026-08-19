package com.hydromart.dto.cart;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
	private Long cartId;
	
	private List<CartItemResponse> items;
	
	private BigDecimal totalAmount;
}
