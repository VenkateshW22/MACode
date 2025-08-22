package com.example.usermanagementapi.service;

import com.example.usermanagementapi.model.User;
import com.example.usermanagementapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Service // Marks this class as a Spring service component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user-specific data (username, password, authorities) from the database.
     * @param email The email (which serves as the username for authentication).
     * @return UserDetails object representing the authenticated user.
     * @throws UsernameNotFoundException If the user with the given email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) // Use your existing findByEmail method
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert your User model's roles (Set<String>) into Spring Security's GrantedAuthority objects
        // Roles in Spring Security typically start with "ROLE_" prefix (e.g., "ROLE_ADMIN").
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Username (email in this case)
                user.getPassword(), // Hashed password
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role)) // Map each role string to SimpleGrantedAuthority
                        .collect(Collectors.toSet()) // Collect them into a Set
        );
    }
}
