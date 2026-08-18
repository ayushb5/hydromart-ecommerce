package com.hydromart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.Category;
import com.hydromart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findBySlug(String slug);

	boolean existsByName(String name);

	boolean existsBySlug(String slug);

	List<Product> findByActiveTrue();

	List<Product> findByActiveFalse();
}
