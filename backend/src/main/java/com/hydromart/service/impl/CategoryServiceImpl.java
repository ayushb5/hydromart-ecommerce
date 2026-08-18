package com.hydromart.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hydromart.dto.category.CategoryRequest;
import com.hydromart.dto.category.CategoryResponse;
import com.hydromart.entity.Category;
import com.hydromart.exception.CategoryAlreadyExistsException;
import com.hydromart.exception.CategoryNotFoundException;
import com.hydromart.repository.CategoryRepository;
import com.hydromart.service.CategoryService;
import com.hydromart.util.SlugUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private CategoryResponse mapToResponse(Category category) {
		CategoryResponse response = new CategoryResponse();

		response.setId(category.getId());
		response.setName(category.getName());
		response.setSlug(category.getSlug());
		response.setDescription(category.getDescription());
		response.setImageUrl(category.getImageUrl());
		response.setActive(category.isActive());
		response.setCreatedAt(category.getCreatedAt());
		response.setUpdatedAt(category.getUpdatedAt());

		return response;
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest request) {
		String slug = SlugUtil.toSlug(request.getName());

		if (categoryRepository.existsByName(request.getName())) {
			throw new CategoryAlreadyExistsException("Category already exists");
		}
		if (categoryRepository.existsBySlug(slug)) {
			throw new CategoryAlreadyExistsException("Category slug already exists");
		}

		Category category = new Category();
		category.setName(request.getName());
		category.setSlug(slug);
		category.setDescription(request.getDescription());
		category.setImageUrl(request.getImageUrl());
		category.setActive(true);
		category.setCreatedAt(LocalDateTime.now());
		category.setUpdatedAt(LocalDateTime.now());

		Category savedCategory = categoryRepository.save(category);

		return mapToResponse(savedCategory);
	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		return categoryRepository.findAll().stream().map(category -> mapToResponse(category)).toList();
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		return mapToResponse(category);
	}

	@Override
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		String slug = SlugUtil.toSlug(request.getName());

		if (!category.getName().equalsIgnoreCase(request.getName())
				&& categoryRepository.existsByName(request.getName())) {
			throw new CategoryAlreadyExistsException("Category already exists");
		}

		if (!category.getSlug().equals(slug) && categoryRepository.existsBySlug(slug)) {
			throw new CategoryAlreadyExistsException("Category slug already exists");
		}

		category.setName(request.getName());
		category.setSlug(slug);
		category.setDescription(request.getDescription());
		category.setImageUrl(request.getImageUrl());
		category.setUpdatedAt(LocalDateTime.now());

		Category updatedCategory = categoryRepository.save(category);
		return mapToResponse(updatedCategory);
	}
	
	@Override
	public CategoryResponse updateCategoryStatus(Long id, boolean isActive) {
		Category category=categoryRepository.findById(id)
				.orElseThrow(()->new CategoryNotFoundException("Category not found"));
		
		category.setActive(isActive);
		category.setUpdatedAt(LocalDateTime.now());
		
		Category updatedCategory=categoryRepository.save(category);
		return mapToResponse(updatedCategory);
	}

	@Override
	public void deleteCategory(Long id) {
		Category category=categoryRepository.findById(id)
				.orElseThrow(()->new CategoryNotFoundException("Category not found"));
		
		categoryRepository.delete(category);
	}

	

}
