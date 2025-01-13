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
    public ResponseEntity<?> createAccount(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "0") BigDecimal initialCredit) {
        try {
            Account account = accountService.createAccount(customerId, initialCredit);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to create account: " + e.getMessage()));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAccountByCustomerId(@PathVariable String customerId) {
        try {
            Account account = accountService.getAccountByCustomerId(customerId);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable UUID id) {
        try {
            Account account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}