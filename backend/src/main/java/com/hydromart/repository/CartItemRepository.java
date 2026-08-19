package com.hydromart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.Cart;
import com.hydromart.entity.CartItem;
import com.hydromart.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
	
	void deleteByCart(Cart cart);
}
