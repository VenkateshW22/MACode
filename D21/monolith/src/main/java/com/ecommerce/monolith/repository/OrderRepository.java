package com.ecommerce.monolith.repository;

import com.ecommerce.monolith.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
