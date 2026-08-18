package com.hydromart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.category.CategoryRequest;
import com.hydromart.dto.category.CategoryResponse;
import com.hydromart.dto.common.StatusUpdateRequest;
import com.hydromart.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request){
		return ResponseEntity.ok(categoryService.createCategory(request));
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request){
		return ResponseEntity.ok(categoryService.updateCategory(id, request));
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<CategoryResponse> updateCategoryStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request){
		return ResponseEntity.ok(categoryService.updateCategoryStatus(id, request.isActive()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		categoryService.deleteCategory(id);
		
		return ResponseEntity.ok("Category deleted successfully");
	}
}
