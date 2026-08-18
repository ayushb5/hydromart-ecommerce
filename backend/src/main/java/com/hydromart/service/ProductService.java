package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.product.ProductRequest;
import com.hydromart.dto.product.ProductResponse;

public interface ProductService {
	ProductResponse createProduct(ProductRequest request);

	List<ProductResponse> getAllProducts();

	ProductResponse getProductById(Long id);

	ProductResponse updateProduct(Long id, ProductRequest request);

	ProductResponse updateProductStatus(Long id, boolean isActive);

	void deleteProduct(Long id);
}
