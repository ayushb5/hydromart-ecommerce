package com.hydromart.dto.auth;

import com.hydromart.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	private String message;
	private String email;
	private Role role;
	private String token;
}
