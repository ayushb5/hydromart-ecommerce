package com.hydromart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.user.UserProfileRequest;
import com.hydromart.dto.user.UserProfileResponse;
import com.hydromart.entity.User;
import com.hydromart.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
		return ResponseEntity.ok(userService.getProfile(authentication.getName()));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<UserProfileResponse> updateProfile(Authentication authentication,@RequestBody UserProfileRequest request){
		return ResponseEntity.ok(userService.updateProfile(authentication.getName(), request));
	}
}
