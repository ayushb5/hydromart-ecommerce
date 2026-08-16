package com.hydromart.service;

import java.util.List;

import com.hydromart.dto.auth.RegisterRequest;
import com.hydromart.entity.User;

public interface UserService {
	User registerUser(RegisterRequest request);
	
	User getUserById(Long id);
	
	List<User> getAllUsers();
}
