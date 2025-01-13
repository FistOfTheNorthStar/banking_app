package com.banking.account.controller;

import com.banking.account.model.Account;
import com.banking.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "0") BigDecimal initialCredit) {
        return ResponseEntity.ok(accountService.createAccount(customerId, initialCredit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(customerId));
    }
}