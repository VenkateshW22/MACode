package com.course.optional.repository;

import com.course.optional.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static final Map<String, User> userDatabase = new HashMap<>();

    static {
        userDatabase.put("101", new User("Alice"));
        userDatabase.put("102", new User("Bob"));
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(userDatabase.get(id));
    }
}
