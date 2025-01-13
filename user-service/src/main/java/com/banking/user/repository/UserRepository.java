package com.banking.user.repository;

import com.banking.user.model.User;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
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
        if (!users.containsKey(id)) {
            User newUser = User.builder()
                .id(id)
                .name("User-" + id)
                .surname("LastName-" + id)
                .build();
            users.put(id, newUser);
        }
        return Optional.ofNullable(users.get(id));
    }

    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }
}