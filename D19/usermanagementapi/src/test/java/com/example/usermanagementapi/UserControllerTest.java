package com.example.usermanagementapi;

import com.example.usermanagementapi.model.User;
import com.example.usermanagementapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        userRepository.deleteAll();
        
        // Register JavaTimeModule for proper LocalDateTime handling
        objectMapper.registerModule(new JavaTimeModule());
        
        // Create a test user
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("Password123!");
        testUser.setRoles(Set.of("ROLE_USER"));
        testUser = userRepository.save(testUser);
    }
    
    @AfterEach
    void tearDown() {
        // Clean up after each test
        userRepository.deleteAll();
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_WithPagination_ShouldReturnPaginatedResults() throws Exception {
        // Create multiple test users
        for (int i = 1; i <= 15; i++) {
            User user = new User();
            user.setName("User " + i);
            user.setEmail("user" + i + "@example.com");
            user.setPassword("Password123!");
            user.setRoles(Set.of("ROLE_USER"));
            userRepository.save(user);
        }
        
        // Test page 0 with 5 items
        mockMvc.perform(get("/api/users/all")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(16)) // 15 new + 1 from setup
                .andExpect(jsonPath("$.totalPages").value(4)) // 16/5 = 4 pages
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.content[0].name").value("Test User")) // First user from setup
                .andExpect(jsonPath("$.content[0].email").value("test@example.com"));
        
        // Test page 1 with 10 items
        mockMvc.perform(get("/api/users/all")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(6)) // Remaining 6 users (16 total - 10 on first page)
                .andExpect(jsonPath("$.number").value(1));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_WithDefaultPagination_ShouldReturnFirstPage() throws Exception {
        // Test without any pagination parameters (should use defaults)
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1)) // Only the test user from setup
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @WithMockUser(roles = "ADMIN")
    void createUser_WhenNameIsInvalid_ShouldReturnBadRequest(String name) throws Exception {
        User user = new User();
        user.setName(name);
        user.setEmail("valid@example.com");
        user.setPassword("Password123!");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_WhenEmailIsInvalid_ShouldReturnBadRequest() throws Exception {
        User user = new User();
        user.setName("Valid Name");
        user.setEmail("invalid-email");
        user.setPassword("Password123!");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_WhenPasswordIsInvalid_ShouldReturnBadRequest() throws Exception {
        User user = new User();
        user.setName("Valid Name");
        user.setEmail("valid@example.com");
        user.setPassword("weak");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_WhenEmailAlreadyExists_ShouldReturnConflict() throws Exception {
        // Create a user with a known email
        User existingUser = new User();
        existingUser.setName("Existing User");
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword("Password123!");
        userRepository.save(existingUser);
        
        // Try to create another user with the same email
        User newUser = new User();
        newUser.setName("New User");
        newUser.setEmail("existing@example.com"); // Same email as existing user
        newUser.setPassword("Password123!");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .with(csrf()))
                .andExpect(status().isConflict());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_WhenInputIsValid_ShouldCreateUser() throws Exception {
        User newUser = new User();
        newUser.setName("New User");
        newUser.setEmail("new.user@example.com");
        newUser.setPassword("Password123!");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("new.user@example.com"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_WhenUserExists_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WhenUserExists_ShouldUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("NewPassword123!");
        
        mockMvc.perform(put("/api/users/{id}", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WhenUserExists_ShouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", testUser.getId())
                .with(csrf()))
                .andExpect(status().isNoContent());
                
        // Verify user was actually deleted
        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 999L)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
