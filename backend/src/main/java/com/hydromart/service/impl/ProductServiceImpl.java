package com.hydromart.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hydromart.dto.product.ProductRequest;
import com.hydromart.dto.product.ProductResponse;
import com.hydromart.entity.Category;
import com.hydromart.entity.Product;
import com.hydromart.exception.CategoryNotFoundException;
import com.hydromart.exception.ProductAlreadyExistsException;
import com.hydromart.exception.ProductNotFoundException;
import com.hydromart.repository.CategoryRepository;
import com.hydromart.repository.ProductRepository;
import com.hydromart.service.ProductService;
import com.hydromart.util.SlugUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	private ProductResponse mapToResponse(Product product) {
		ProductResponse response = new ProductResponse();
		
		response.setId(product.getId());
		response.setCategoryId(product.getCategory().getId());
		response.setCategoryName(product.getCategory().getName());
		response.setName(product.getName());
		response.setSlug(product.getSlug());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setStockQuantity(product.getStockQuantity());
		response.setImageUrl(product.getImageUrl());
		response.setActive(product.isActive());
		response.setCreatedAt(product.getCreatedAt());
		response.setUpdatedAt(product.getUpdatedAt());
		
		return response;
	}

	@Override
	public ProductResponse createProduct(ProductRequest request) {

		if (productRepository.existsByName(request.getName())) {
			throw new ProductAlreadyExistsException("Product already exists");
		}

		String slug = SlugUtil.toSlug(request.getName());

		if (productRepository.existsBySlug(slug)) {
			throw new ProductAlreadyExistsException("Product slug already exists");
		}

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		Product product = new Product();
		product.setCategory(category);
		product.setName(request.getName());
		product.setSlug(slug);
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setImageUrl(request.getImageUrl());
		product.setActive(true);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);

		return mapToResponse(savedProduct);
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		return productRepository.findAll()
				.stream()
				.map(product->mapToResponse(product))
				.toList();
	}

	@Override
	public ProductResponse getProductById(Long id) {
		Product product=productRepository.findById(id)
				.orElseThrow(()->new ProductNotFoundException("Product not found"));
		
		return mapToResponse(product);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product product=productRepository.findById(id)
				.orElseThrow(()->new ProductNotFoundException("Product not found"));
		
		String slug=SlugUtil.toSlug(request.getName());
		
		if(!product.getName().equalsIgnoreCase(request.getName())
				&& productRepository.existsByName(request.getName())) {
			throw new ProductAlreadyExistsException("Product already exists");
		}
		
		if(!product.getSlug().equalsIgnoreCase(slug) && productRepository.existsBySlug(slug)) {
			throw new ProductAlreadyExistsException("Product slug already exists");
		}
		
		Category category=categoryRepository.findById(request.getCategoryId())
				.orElseThrow(()->new CategoryNotFoundException("Category not found"));
		
		product.setCategory(category);
		product.setName(request.getName());
		product.setSlug(slug);
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setImageUrl(request.getImageUrl());
		product.setUpdatedAt(LocalDateTime.now());
		
		Product updatedProduct=productRepository.save(product);
		
		return mapToResponse(updatedProduct);
	}
	
	@Override
	public ProductResponse updateProductStatus(Long id, boolean isActive) {
		Product product=productRepository.findById(id)
				.orElseThrow(()->new ProductNotFoundException("Product not found"));
		
		product.setActive(isActive);
		product.setUpdatedAt(LocalDateTime.now());
	
		Product updatedProduct=productRepository.save(product);
		
		return mapToResponse(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product=productRepository.findById(id)
				.orElseThrow(()->new ProductNotFoundException("Product not found"));
		
		productRepository.delete(product);
	}

}
