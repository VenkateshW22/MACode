package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
