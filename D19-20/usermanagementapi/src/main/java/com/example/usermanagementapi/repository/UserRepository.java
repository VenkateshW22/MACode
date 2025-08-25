package com.example.usermanagementapi.repository;

import com.example.usermanagementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Not strictly necessary as JpaRepository already makes it a Spring bean, but good for clarity
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically provides CRUD methods like save(), findById(), findAll(), deleteById()

    // Derived query method: Spring Data JPA will automatically generate the SQL query for this method
    // to find a User by their email address.
    Optional<User> findByEmail(String email);

    // Another example: find users whose name contains a given string (case-insensitive)
    // List<User> findByNameContainingIgnoreCase(String name);
}
