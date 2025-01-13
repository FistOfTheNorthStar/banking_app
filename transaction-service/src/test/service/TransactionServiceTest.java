package com.banking.transaction.service;

import com.banking.transaction.model.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void createTransaction_Success() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        when(transactionRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Transaction result = transactionService.createTransaction(accountId, amount);

        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
        assertEquals(amount, result.getAmount());
        assertEquals("CREDIT", result.getType());
    }

    @Test
    void getTransactionsByAccountId_Success() {
        UUID accountId = UUID.randomUUID();
        List<Transaction> mockTransactions = List.of(
            Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(100))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build(),
            Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(50))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build()
        );

        when(transactionRepository.findByAccountId(accountId)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getTransactionsByAccountId(accountId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(accountId, result.get(0).getAccountId());
        assertEquals(accountId, result.get(1).getAccountId());
    }

    @Test
    void getTransactionsByAccountId_NoTransactions() {
        UUID accountId = UUID.randomUUID();
        when(transactionRepository.findByAccountId(accountId)).thenReturn(List.of());

        List<Transaction> result = transactionService.getTransactionsByAccountId(accountId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}