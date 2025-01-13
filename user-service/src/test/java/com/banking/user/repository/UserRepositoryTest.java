package com.banking.user.repository;

import com.banking.user.model.User;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userRepository.init(); // Manually call init since @PostConstruct won't be triggered
    }

    @Test
    void findById_ExistingUser_Success() {
        // When
        Optional<User> result = userRepository.findById("1");

        // Then
        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals("1", user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void findById_NonExistingUser_ReturnsEmpty() {
        // When
        Optional<User> result = userRepository.findById("999");

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void init_CreatesTestData() {
        // Verify first test user
        Optional<User> user1 = userRepository.findById("1");
        assertTrue(user1.isPresent());
        assertEquals("John", user1.get().getName());
        assertEquals("Doe", user1.get().getSurname());

        // Verify second test user
        Optional<User> user2 = userRepository.findById("2");
        assertTrue(user2.isPresent());
        assertEquals("Jane", user2.get().getName());
        assertEquals("Smith", user2.get().getSurname());
    }

    @Test
    void findById_Concurrent_ThreadSafe() throws InterruptedException {
        // This test verifies that the ConcurrentHashMap is thread-safe
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                userRepository.findById("1");
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                userRepository.findById("2");
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Verify data integrity after concurrent access
        Optional<User> user1 = userRepository.findById("1");
        Optional<User> user2 = userRepository.findById("2");

        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals("John", user1.get().getName());
        assertEquals("Jane", user2.get().getName());
    }
}