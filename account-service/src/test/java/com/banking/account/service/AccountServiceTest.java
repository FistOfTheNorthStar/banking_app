package com.banking.account.service;

import com.banking.account.model.Account;
import com.banking.account.repository.AccountRepository;
import com.banking.account.client.TransactionServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionServiceClient transactionServiceClient;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository, transactionServiceClient);
    }

    @Test
    void createAccount_WithZeroCredit_Success() {
        String customerId = "1";
        BigDecimal initialCredit = BigDecimal.ZERO;

        when(accountRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Account result = accountService.createAccount(customerId, initialCredit);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(initialCredit, result.getBalance());
        verify(transactionServiceClient, never()).createTransaction(any(), any());
    }

    @Test
    void createAccount_WithPositiveCredit_Success() {
        String customerId = "1";
        BigDecimal initialCredit = BigDecimal.valueOf(100);

        when(accountRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(transactionServiceClient).createTransaction(any(), any());

        Account result = accountService.createAccount(customerId, initialCredit);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(initialCredit, result.getBalance());
        verify(transactionServiceClient, times(1)).createTransaction(any(), eq(initialCredit));
    }

    @Test
    void getAccount_Success() {
        UUID accountId = UUID.randomUUID();
        Account account = Account.builder()
                .id(accountId)
                .customerId("1")
                .balance(BigDecimal.ZERO)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.getAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
    }

    @Test
    void getAccount_NotFound() {
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountService.getAccount(accountId));
    }
}