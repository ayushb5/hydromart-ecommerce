package com.hydromart.dto.cart;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {
	private Long productId;
	
	private String productName;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	private BigDecimal subtotal;
}
