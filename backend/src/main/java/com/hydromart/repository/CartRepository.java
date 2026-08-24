package com.hydromart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.Cart;
import com.hydromart.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	@EntityGraph(attributePaths = { "cartItems", "cartItems.product" })
	Optional<Cart> findByUser(User user);
}
