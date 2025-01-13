package com.banking.transaction.repository;

import com.banking.transaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {
    private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public List<Transaction> findByAccountId(UUID accountId) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getAccountId().equals(accountId))
                .toList();
    }
}