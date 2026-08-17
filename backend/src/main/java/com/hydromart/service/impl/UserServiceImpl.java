package com.hydromart.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hydromart.dto.auth.AuthResponse;
import com.hydromart.dto.auth.LoginRequest;
import com.hydromart.dto.auth.RegisterRequest;
import com.hydromart.dto.auth.RegisterResponse;
import com.hydromart.entity.User;
import com.hydromart.enums.Role;
import com.hydromart.exception.EmailAlreadyExistsException;
import com.hydromart.exception.InvalidCredentialsException;
import com.hydromart.exception.UserNotFoundException;
import com.hydromart.repository.UserRepository;
import com.hydromart.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public RegisterResponse registerUser(RegisterRequest request) {
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		if (existingUser.isPresent()) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.CUSTOMER);
		user.setActive(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		User savedUser=userRepository.save(user);
		return new RegisterResponse("Registration successful",savedUser.getEmail());
	}

	@Override
	public AuthResponse loginUser(LoginRequest request) {
		User user=userRepository.findByEmail(request.getEmail())
				.orElseThrow(()->new InvalidCredentialsException("Invalid email or password"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {			
			throw new InvalidCredentialsException("Invalid email or password");
		}
		
		return new AuthResponse("Login successful",user.getEmail(),user.getRole());
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
