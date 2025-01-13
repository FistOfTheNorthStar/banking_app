package com.banking.user.repository;

import com.banking.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userRepository.init();
    }

    @Test
    void init_CreatesTestData() {
        Optional<User> user1 = userRepository.findById("1");
        assertTrue(user1.isPresent());
        assertEquals("John", user1.get().getName());
        assertEquals("Doe", user1.get().getSurname());

        Optional<User> user2 = userRepository.findById("2");
        assertTrue(user2.isPresent());
        assertEquals("Jane", user2.get().getName());
        assertEquals("Smith", user2.get().getSurname());
    }

    @Test
    void findById_ExistingUser_Success() {
        Optional<User> result = userRepository.findById("1");
        
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getSurname());
    }

    @Test
    void findById_NewUser_CreatesUser() {
        String newUserId = "999";
        Optional<User> result = userRepository.findById(newUserId);

        assertTrue(result.isPresent());
        assertEquals(newUserId, result.get().getId());
        assertEquals("User-999", result.get().getName());
        assertEquals("LastName-999", result.get().getSurname());
    }
    
    @Test
    void save_NewUser_Success() {
        User newUser = User.builder()
            .id("3")
            .name("Test")
            .surname("User")
            .build();

        User savedUser = userRepository.save(newUser);

        assertEquals(newUser.getId(), savedUser.getId());
        assertEquals(newUser.getName(), savedUser.getName());
        assertEquals(newUser.getSurname(), savedUser.getSurname());

        Optional<User> retrieved = userRepository.findById("3");
        assertTrue(retrieved.isPresent());
        assertEquals(newUser.getName(), retrieved.get().getName());
    }
}