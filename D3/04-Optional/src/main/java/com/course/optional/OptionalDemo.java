package com.course.optional;

import com.course.optional.model.User;
import com.course.optional.repository.UserRepository;
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();

        demonstrateFoundUser(repo, "101");
        demonstrateNotFoundUser(repo, "999");
        demonstrateChaining(repo, "102");
    }

    public static void demonstrateFoundUser(UserRepository repo, String id) {
        System.out.println("\n--- Scenario 1: User exists ---");
        repo.findById(id).ifPresent(user -> System.out.println("Found: " + user.getName()));
    }

    public static void demonstrateNotFoundUser(UserRepository repo, String id) {
        System.out.println("\n--- Scenario 2: User does not exist ---");
        User user = repo.findById(id).orElse(new User("Guest"));
        System.out.println("Final user: " + user.getName());
    }

    public static void demonstrateChaining(UserRepository repo, String id) {
        System.out.println("\n--- Scenario 3: Chaining operations ---");
        String userName = repo.findById(id)
                .map(User::getName)
                .orElse("Unknown User");
        System.out.println("Username for ID " + id + ": " + userName);
    }
}
