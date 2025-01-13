package com.banking.user.service;

import com.banking.user.client.AccountServiceClient;
import com.banking.user.client.TransactionServiceClient;
import com.banking.user.dto.AccountDTO;
import com.banking.user.dto.TransactionDTO;
import com.banking.user.model.User;
import com.banking.user.model.UserAccountInfo;
import com.banking.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private TransactionServiceClient transactionServiceClient;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, accountServiceClient, transactionServiceClient);
    }

    @Test
    void getUserAccountInfo_Success() {
        // Given
        String userId = "1";
        UUID accountId = UUID.randomUUID();
        
        User user = User.builder()
                .id(userId)
                .name("John")
                .surname("Doe")
                .build();

        AccountDTO account = AccountDTO.builder()
                .id(accountId)
                .customerId(userId)
                .balance(BigDecimal.valueOf(1000))
                .build();

        List<TransactionDTO> transactions = List.of(
            TransactionDTO.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(500))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build(),
            TransactionDTO.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(300))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountServiceClient.getAccountByCustomerId(userId)).thenReturn(account);
        when(transactionServiceClient.getTransactionsByAccountId(accountId.toString()))
                .thenReturn(transactions);

        // When
        UserAccountInfo result = userService.getUserAccountInfo(userId);

        // Then
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurname(), result.getSurname());
        assertEquals(account.getBalance(), result.getBalance());
        assertEquals(2, result.getTransactions().size());
        assertEquals(transactions.get(0).getAmount(), result.getTransactions().get(0).getAmount());
        assertEquals(transactions.get(1).getAmount(), result.getTransactions().get(1).getAmount());
    }

    @Test
    void getUserAccountInfo_UserNotFound() {
        // Given
        String userId = "999";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.getUserAccountInfo(userId));
    }

    @Test
    void getUserAccountInfo_AccountServiceFailure() {
        // Given
        String userId = "1";
        User user = User.builder()
                .id(userId)
                .name("John")
                .surname("Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountServiceClient.getAccountByCustomerId(anyString()))
                .thenThrow(new RuntimeException("Account service unavailable"));

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.getUserAccountInfo(userId));
    }

    @Test
    void getUserAccountInfo_TransactionServiceFailure() {
        // Given
        String userId = "1";
        UUID accountId = UUID.randomUUID();
        
        User user = User.builder()
                .id(userId)
                .name("John")
                .surname("Doe")
                .build();

        AccountDTO account = AccountDTO.builder()
                .id(accountId)
                .customerId(userId)
                .balance(BigDecimal.valueOf(1000))
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountServiceClient.getAccountByCustomerId(userId)).thenReturn(account);
        when(transactionServiceClient.getTransactionsByAccountId(anyString()))
                .thenThrow(new RuntimeException("Transaction service unavailable"));

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.getUserAccountInfo(userId));
    }
}