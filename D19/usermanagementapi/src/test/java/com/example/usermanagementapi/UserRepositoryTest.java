package com.example.usermanagementapi;

import com.example.usermanagementapi.model.User;
import com.example.usermanagementapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the UserRepository.
 *
 * @DataJpaTest: This annotation is specifically for JPA tests. It configures
 * a Spring application context with an in-memory embedded database (like H2)
 * and scans for @Entity and Spring Data JPA repositories. It also sets up
 * a TestEntityManager for interacting with the persistence context.
 *
 * It's lighter than @SpringBootTest as it doesn't load the full application context,
 * making tests faster and more focused on the data layer.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // The repository we are testing

    @Autowired
    private TestEntityManager entityManager; // Used to interact with the database in tests (e.g., persist entities directly)

    private User user1;
    private User user2;

    /**
     * @BeforeEach: This method runs before each test method.
     * It ensures a clean state for each test and populates the database with
     * consistent test data.
     */
    @BeforeEach
    void setUp() {
        // Clear all entities from the database before each test to ensure isolation.
        // Using deleteAllInBatch() is often faster for @DataJpaTest.
        userRepository.deleteAllInBatch();

        // Create sample user data with all required fields
        user1 = new User();
        user1.setName("Alice Wonderland");
        user1.setEmail("alice@example.com");
        user1.setPassword("Password123!");
        user1.setRoles(Set.of("ROLE_USER"));
        
        user2 = new User();
        user2.setName("Bob The Builder");
        user2.setEmail("bob@example.com");
        user2.setPassword("Password123!");
        user2.setRoles(Set.of("ROLE_USER"));

        // Persist the users using TestEntityManager. This ensures they are saved
        // and managed by the JPA context, and their auto-generated IDs are set.
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush(); // Flush pending changes to the database
        entityManager.clear(); // Clear the persistence context to ensure subsequent reads come from the DB
    }

    /**
     * Test case for finding a user by email when the user exists.
     */
    @Test
    void testFindByEmailFound() {
        // When: We try to find a user by an existing email
        Optional<User> foundUser = userRepository.findByEmail(user1.getEmail());

        // Then: The user should be found and its details should match
        assertThat(foundUser).isPresent(); // Assert that an Optional contains a value
        assertThat(foundUser.get().getName()).isEqualTo(user1.getName());
        assertThat(foundUser.get().getEmail()).isEqualTo(user1.getEmail());
        assertThat(foundUser.get().getId()).isEqualTo(user1.getId()); // Verify ID is also matched
    }

    /**
     * Test case for finding a user by email when the user does not exist.
     */
    @Test
    void testFindByEmailNotFound() {
        // When: We try to find a user by a non-existent email
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then: The Optional should be empty
        assertThat(foundUser).isNotPresent(); // Assert that an Optional is empty
    }

    /**
     * Test case for saving a new user.
     * Verifies that the user is saved and an ID is assigned.
     */
    @Test
    void testSaveUser() {
        // Given: A new user object with all required fields
        User newUser = new User();
        newUser.setName("Charlie Chaplin");
        newUser.setEmail("charlie@example.com");
        newUser.setPassword("Password123!");
        newUser.setRoles(Set.of("ROLE_USER"));

        // When: The user is saved
        User savedUser = userRepository.save(newUser);

        // Then: The saved user should not be null, should have an ID,
        // and its details should match the original.
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Charlie Chaplin");
        assertThat(savedUser.getEmail()).isEqualTo("charlie@example.com");

        // Verify that the user can be retrieved from the database
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get()).isEqualTo(savedUser);
    }

    /**
     * Test case for updating an existing user.
     * Verifies that the user's details are updated in the database.
     */
    @Test
    void testUpdateUser() {
        // Given: An existing user (user1 from setUp)
        String newName = "Alice In Wonderland Updated";
        String newEmail = "alice.updated@example.com";

        user1.setName(newName);
        user1.setEmail(newEmail);

        // When: The user is updated
        User updatedUser = userRepository.save(user1);

        // Then: The updated user's details should reflect the changes
        assertThat(updatedUser.getName()).isEqualTo(newName);
        assertThat(updatedUser.getEmail()).isEqualTo(newEmail);

        // Verify the update by fetching from the database
        Optional<User> retrievedUser = userRepository.findById(user1.getId());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getName()).isEqualTo(newName);
        assertThat(retrievedUser.get().getEmail()).isEqualTo(newEmail);
    }

    /**
     * Test case for deleting a user by ID.
     * Verifies that the user is no longer present in the database.
     */
    @Test
    void testDeleteUser() {
        // Given: An existing user (user1 from setUp)
        Long userIdToDelete = user1.getId();

        // When: The user is deleted
        userRepository.deleteById(userIdToDelete);

        // Then: The user should no longer be found in the database
        Optional<User> deletedUser = userRepository.findById(userIdToDelete);
        assertThat(deletedUser).isNotPresent();
    }

    /**
     * Test case for retrieving all users.
     * Verifies that all initially saved users are retrieved.
     */
    @Test
    void testFindAllUsers() {
        // When: All users are retrieved
        List<User> allUsers = userRepository.findAll();

        // Then: The list should contain all users saved in setUp
        // (initialUser1, initialUser2, and the 25 manyUsers)
        assertThat(allUsers).hasSize(2 ); // 2 from user1 and user2
    }

    /**
     * Test case to ensure email uniqueness constraint is enforced.
     * The test expects a DataIntegrityViolationException when attempting to save a user
     * with an email that already exists in the database.
     */
    @Test
    void testSaveUserWithDuplicateEmail() {
        // Given: A new user with an email that already exists
        User duplicateEmailUser = new User();
        duplicateEmailUser.setName("Duplicate Name");
        duplicateEmailUser.setEmail(user1.getEmail());
        duplicateEmailUser.setPassword("Password123!");
        duplicateEmailUser.setRoles(Set.of("ROLE_USER"));

        // When/Then: Attempting to save a user with a duplicate email should throw an exception
        org.junit.jupiter.api.Assertions.assertThrows(
            org.springframework.dao.DataIntegrityViolationException.class,
            () -> userRepository.saveAndFlush(duplicateEmailUser)
        );
    }
}
