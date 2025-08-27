package com.ecommerce.monolith.repository;

import com.ecommerce.monolith.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
