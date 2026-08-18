package com.hydromart.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Long id;
	private Long categoryId;
	private String categoryName;
	private String name;
	private String slug;
	private String description;
	private BigDecimal price;
	private Integer stockQuantity;
	private String imageUrl;
	private boolean active;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
