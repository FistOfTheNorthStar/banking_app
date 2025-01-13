package com.banking.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.banking.account.client.TransactionServiceClient;
import com.banking.account.model.Account;
import com.banking.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionServiceClient transactionServiceClient;

    public Account createAccount(String customerId, BigDecimal initialCredit) {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        account = accountRepository.save(account);

        if (initialCredit.compareTo(BigDecimal.ZERO) > 0) {
            transactionServiceClient.createTransaction(account.getId(), initialCredit);
            account.setBalance(initialCredit);
            accountRepository.save(account);
        }

        return account;
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account getAccountByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Account not found for customer"));
    }
}