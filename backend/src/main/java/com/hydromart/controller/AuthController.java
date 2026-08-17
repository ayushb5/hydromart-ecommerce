package com.hydromart.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydromart.dto.auth.AuthResponse;
import com.hydromart.dto.auth.LoginRequest;
import com.hydromart.dto.auth.RegisterRequest;
import com.hydromart.dto.auth.RegisterResponse;
import com.hydromart.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
		RegisterResponse response = userService.registerUser(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
		AuthResponse response=userService.loginUser(request);
		return ResponseEntity.ok(response);
	}
}
