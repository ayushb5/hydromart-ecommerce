package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.auth.AuthResponse;
import com.hydromart.dto.auth.LoginRequest;
import com.hydromart.dto.auth.RegisterRequest;
import com.hydromart.dto.auth.RegisterResponse;
import com.hydromart.dto.user.UserProfileRequest;
import com.hydromart.dto.user.UserProfileResponse;
import com.hydromart.entity.User;

public interface UserService {
	RegisterResponse registerUser(RegisterRequest request);

	AuthResponse loginUser(LoginRequest request);

	User getUserById(Long id);

	List<User> getAllUsers();

	UserProfileResponse getProfile(String email);

	UserProfileResponse updateProfile(String email, UserProfileRequest request);
}
