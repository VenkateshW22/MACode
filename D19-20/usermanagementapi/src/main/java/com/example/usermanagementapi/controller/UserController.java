package com.example.usermanagementapi.controller;

import com.example.usermanagementapi.model.User;
import com.example.usermanagementapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Keep if you prefer annotation-based CORS here
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Constructor injection for both
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     * HTTP Method: POST
     * Endpoint: /api/users/register
     * Request Body: User object (JSON) with name, email, and plain text password.
     * Response: Created User object with HTTP Status 201 (Created)
     */
    @PostMapping("/register") // New endpoint for registration
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict if email exists
        }
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Assign a default role (e.g., "ROLE_USER") for new registrations
        user.setRoles(Collections.singleton("ROLE_USER")); // Assign default role
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Creates new users (batch creation - typically for ADMIN).
     * HTTP Method: POST
     * Endpoint: /api/users
     * Request Body: List of User objects (JSON)
     * Response: Created User objects with HTTP Status 201 (Created)
     */
    @PostMapping // This endpoint is now protected by hasRole("ADMIN")
    public ResponseEntity<List<User>> createUsers(@Valid @RequestBody List<User> users) {
        // Encode passwords and assign roles for each user in the list
        users.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Default role, or expect roles in request body if ADMIN can specify roles
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(Collections.singleton("ROLE_USER"));
            }
        });
        List<User> savedUsers = userRepository.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsers);
    }

    /**
     * Retrieves all users (typically for ADMIN).
     * HTTP Method: GET
     * Endpoint: /api/users
     * Response: List of User objects with HTTP Status 200 (OK)
     */
    @GetMapping // This endpoint is now protected by hasRole("ADMIN")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    /**
     * Retrieves all users with pagination support.
     * <p>
     * This endpoint returns a paginated list of all users in the system.
     * The response includes pagination metadata such as total elements, total pages, etc.
     *
     * @param pageable Pagination information (page number, size, and sorting parameters)
     * @return A {@link org.springframework.data.domain.Page} containing the list of users and pagination information
     * @apiNote Example usage: /api/users/all?page=0&size=10&sort=name,asc
     * @apiParam (Query Parameters) {number} [page=0] Zero-based page index (0..N)
     * @apiParam (Query Parameters) {number} [size=20] The size of the page to be returned
     * @apiParam (Query Parameters) {string} [sort] Sorting criteria in the format: property(,asc|desc).
     *           Default sort order is ascending. Multiple sort criteria are supported.
     * @apiExample Example request:
     * GET /api/users/all?page=0&size=10&sort=name,asc
     * @apiSuccess (200) {Object[]} content List of users
     * @apiSuccess (200) {number} totalElements Total number of users
     * @apiSuccess (200) {number} totalPages Total number of pages
     * @apiSuccess (200) {number} size Number of users per page
     * @apiSuccess (200) {number} number Current page number (zero-based)
     */
    @GetMapping("/all")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Retrieves a user by ID.
     * HTTP Method: GET
     * Endpoint: /api/users/{id}
     * Path Variable: id (Long)
     * Response: User object with HTTP Status 200 (OK) if found, 404 (Not Found) otherwise
     */
    @GetMapping("/{id}") // Protected by hasAnyRole("USER", "ADMIN")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing user by ID.
     * HTTP Method: PUT
     * Endpoint: /api/users/{id}
     * Path Variable: id (Long)
     * Request Body: User object (JSON) with updated details
     * Response: Updated User object with HTTP Status 200 (OK) if found, 404 (Not Found) otherwise
     */
    @PutMapping("/{id}") // Protected by hasAnyRole("USER", "ADMIN")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userDetails.getName());
                    existingUser.setEmail(userDetails.getEmail());
                    // Only update password if a new one is provided and not empty
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    // Roles should typically be updated by an ADMIN or via a separate endpoint
                    // For now, let's not allow regular users to change their own roles through this endpoint.
                    // If an ADMIN updates, they might send roles, so handle it:
                    if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
                        existingUser.setRoles(userDetails.getRoles());
                    }

                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a user by ID.
     * HTTP Method: DELETE
     * Endpoint: /api/users/{id}
     * Path Variable: id (Long)
     * Response: HTTP Status 204 (No Content) if successful, 404 (Not Found) if user doesn't exist
     */
    @DeleteMapping("/{id}") // Protected by hasAnyRole("USER", "ADMIN") - consider making it ADMIN only
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all users with pagination and sorting capabilities (permitAll).
     * HTTP Method: GET
     * Endpoint: /api/users/page
     * Query Parameters: page, size, sort
     * Response: Page object containing a list of User objects with HTTP Status 200 (OK)
     */

    @GetMapping("/page") // Publicly accessible as per SecurityConfig
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
