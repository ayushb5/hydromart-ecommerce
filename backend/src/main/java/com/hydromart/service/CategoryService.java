package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.category.CategoryRequest;
import com.hydromart.dto.category.CategoryResponse;

public interface CategoryService {
	CategoryResponse createCategory(CategoryRequest request);
	
	List<CategoryResponse> getAllCategories();
	
	CategoryResponse getCategoryById(Long id);
	
	CategoryResponse updateCategory(Long id, CategoryRequest request);
	
	CategoryResponse updateCategoryStatus(Long id, boolean isActive);
	
	void deleteCategory(Long id);
}
