package com.hydromart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydromart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
