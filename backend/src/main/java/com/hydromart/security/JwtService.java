package com.hydromart.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(String email) {
		return Jwts.builder().subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSigningKey()).compact();
	}

	public String extractEmail(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, String email) {
		return extractEmail(token).equals(email)
				&& !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaims(token)
				.getExpiration()
				.before(new Date());
	}

	private Claims extractClaims(String token) {
		return Jwts.parser().verifyWith((javax.crypto.SecretKey) getSigningKey()).build().parseSignedClaims(token)
				.getPayload();
	}
}
