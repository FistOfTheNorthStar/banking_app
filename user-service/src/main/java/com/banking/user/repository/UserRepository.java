package com.banking.user.repository;

import com.banking.user.model.User;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Initialize with some test data
        users.put("1", User.builder()
                .id("1")
                .name("John")
                .surname("Doe")
                .build());
        users.put("2", User.builder()
                .id("2")
                .name("Jane")
                .surname("Smith")
                .build());
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }
}