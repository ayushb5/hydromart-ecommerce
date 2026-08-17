package com.hydromart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByName(String name);
	
	Optional<Category> findBySlug(String slug);
	
	boolean existsByName(String name);
	
	boolean existsBySlug(String slug);
	
}
