package com.example.usermanagementapi.config;

import com.example.usermanagementapi.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration // Marks this class as a source of bean definitions
@EnableWebSecurity // Enables Spring Security's web security features
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Defines the SecurityFilterChain, which is the core of Spring Security's configuration.
     * It configures authorization rules, form login, CORS, CSRF, and session management.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with a custom source
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs (common with token-based auth)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register").permitAll() // Allow registration without authentication
                        .requestMatchers("/api/users/page").permitAll() // Allow guests to see users with pagination
                        .requestMatchers("/api/users").hasRole("ADMIN") // Only ADMIN can create multiple users or get all users without pagination
                        .requestMatchers("/api/users/{id}").hasAnyRole("USER", "ADMIN") // Both USER and ADMIN can view/update/delete specific users
                        // Note: More granular control needed if only ADMIN can delete, USER can only update their own profile etc.
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(org.springframework.security.config.Customizer.withDefaults()) // Enable HTTP Basic Authentication
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Use stateless sessions (good for REST APIs)

        return http.build(); // Build the security filter chain
    }

    /**
     * Configures the PasswordEncoder bean. BCryptPasswordEncoder is highly recommended.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationProvider to use our CustomUserDetailsService and PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Set our custom UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Set the password encoder
        return authProvider;
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing).
     * This is an alternative to @CrossOrigin on the controller if you want global CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Allow all origins (for development)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (e.g., cookies, HTTP Basic auth)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this CORS config to all paths
        return source;
    }
}
