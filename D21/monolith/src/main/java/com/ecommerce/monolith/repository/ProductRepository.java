package com.ecommerce.monolith.repository;

import com.ecommerce.monolith.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
