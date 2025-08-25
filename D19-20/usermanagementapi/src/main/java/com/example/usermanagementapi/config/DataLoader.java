package com.example.usermanagementapi.config;

import com.example.usermanagementapi.model.User;
import com.example.usermanagementapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create admin user if not exists
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin@123"));
                admin.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER"));
                userRepository.save(admin);
                System.out.println("Created admin user: admin@example.com");
            }

            // Create regular user if not exists
            if (userRepository.findByEmail("user@example.com").isEmpty()) {
                User user = new User();
                user.setName("Regular User");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user@123"));
                user.setRoles(Set.of("ROLE_USER"));
                userRepository.save(user);
                System.out.println("Created regular user: user@example.com");
            }
        };
    }
}
