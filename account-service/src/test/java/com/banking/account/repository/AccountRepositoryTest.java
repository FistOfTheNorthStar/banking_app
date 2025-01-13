package com.banking.account.repository;

import com.banking.account.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {

    private final AccountRepository accountRepository = new AccountRepository();

    @Test
    public void saveAndFind_Success() {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .customerId("1")
                .balance(BigDecimal.ZERO)
                .build();

        Account saved = accountRepository.save(account);
        assertNotNull(saved);

        var found = accountRepository.findById(account.getId());
        assertTrue(found.isPresent());
        assertEquals(account.getCustomerId(), found.get().getCustomerId());
    }

    @Test
    public void findByCustomerId_Success() {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .customerId("1")
                .balance(BigDecimal.ZERO)
                .build();

        accountRepository.save(account);

        var found = accountRepository.findByCustomerId("1");
        assertTrue(found.isPresent());
        assertEquals(account.getId(), found.get().getId());
    }
}