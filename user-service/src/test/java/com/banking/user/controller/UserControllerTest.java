// src/test/java/com/banking/user/controller/UserControllerTest.java
package com.banking.user.controller;

import com.banking.user.model.UserAccountInfo;
import com.banking.user.service.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void getUserAccountInfo_Success() {
        // Given
        String userId = "1";
        UserAccountInfo mockInfo = UserAccountInfo.builder()
                .name("John")
                .surname("Doe")
                .balance(BigDecimal.valueOf(1000))
                .transactions(new ArrayList<>())
                .build();

        when(userService.getUserAccountInfo(userId)).thenReturn(mockInfo);

        // When
        ResponseEntity<UserAccountInfo> response = userController.getUserAccountInfo(userId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        UserAccountInfo result = response.getBody();
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals(BigDecimal.valueOf(1000), result.getBalance());
        assertTrue(result.getTransactions().isEmpty());
    }

    @Test
    void getUserAccountInfo_UserNotFound() {
        // Given
        String userId = "999";
        when(userService.getUserAccountInfo(userId))
                .thenThrow(new RuntimeException("User not found"));

        // When/Then
        Exception exception = assertThrows(RuntimeException.class, () -> 
            userController.getUserAccountInfo(userId));
        assertEquals("User not found", exception.getMessage());
    }
}