package com.banking.transaction.repository;

import com.banking.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepository();
    }

    @Test
    public void save_Success() {
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100))
                .type("CREDIT")
                .build();

        Transaction saved = transactionRepository.save(transaction);

        assertNotNull(saved);
        assertEquals(transaction.getId(), saved.getId());
        assertEquals(transaction.getAccountId(), saved.getAccountId());
        assertEquals(transaction.getAmount(), saved.getAmount());
    }

    @Test
    public void findByAccountId_Success() {
        UUID accountId = UUID.randomUUID();
        Transaction transaction1 = Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(100))
                .type("CREDIT")
                .build();
        Transaction transaction2 = Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(50))
                .type("CREDIT")
                .build();

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        List<Transaction> found = transactionRepository.findByAccountId(accountId);

        assertNotNull(found);
        assertEquals(2, found.size());
        assertTrue(found.stream().allMatch(t -> t.getAccountId().equals(accountId)));
    }

    @Test
    public void findByAccountId_NoTransactions() {
        UUID accountId = UUID.randomUUID();
        List<Transaction> found = transactionRepository.findByAccountId(accountId);

        assertNotNull(found);
        assertTrue(found.isEmpty());
    }
}