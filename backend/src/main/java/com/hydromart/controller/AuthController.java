package com.hydromart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.auth.RegisterRequest;
import com.hydromart.entity.User;
import com.hydromart.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request){
		User user=userService.registerUser(request);
		return ResponseEntity.ok(user);
	}
}
