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
import com.hydromart.dto.user.UserProfileRequest;
import com.hydromart.dto.user.UserProfileResponse;
import com.hydromart.entity.User;
import com.hydromart.enums.Role;
import com.hydromart.exception.EmailAlreadyExistsException;
import com.hydromart.exception.InvalidCredentialsException;
import com.hydromart.exception.UserNotFoundException;
import com.hydromart.repository.UserRepository;
import com.hydromart.security.JwtService;
import com.hydromart.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	private UserProfileResponse mapToUserProfileResponse(User user) {
		UserProfileResponse response=new UserProfileResponse();
		
		response.setId(user.getId());
		response.setEmail(user.getEmail());
		response.setRole(user.getRole().name());
		response.setPhone(user.getPhone());
		response.setAddressLine1(user.getAddressLine1());
		response.setAddressLine2(user.getAddressLine2());
		response.setCity(user.getCity());
		response.setState(user.getState());
		response.setPostalCode(user.getPostalCode());
		response.setCountry(user.getCountry());
		
		return response;
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
		
		String token=jwtService.generateToken(user.getEmail());
		
		return new AuthResponse("Login successful",user.getEmail(),user.getRole(),token);
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

	@Override
	public UserProfileResponse getProfile(String email) {
		User user=userRepository.findByEmail(email)
				.orElseThrow(()->new UserNotFoundException("User not found"));
		
		return mapToUserProfileResponse(user);
	}

	@Override
	public UserProfileResponse updateProfile(String email, UserProfileRequest request) {
		User user=userRepository.findByEmail(email)
				.orElseThrow(()->new UserNotFoundException("User not found"));
		
		user.setPhone(request.getPhone());
		user.setAddressLine1(request.getAddressLine1());
		user.setAddressLine2(request.getAddressLine2());
		user.setCity(request.getCity());
		user.setState(request.getState());
		user.setPostalCode(request.getPostalCode());
		user.setCountry(request.getCountry());
		
		user.setUpdatedAt(LocalDateTime.now());
		
		User updatedUser=userRepository.save(user);
		
		return mapToUserProfileResponse(updatedUser);
	}

}
