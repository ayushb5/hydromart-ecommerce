package com.hydromart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.Order;
import com.hydromart.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(User user);
}
