package com.banking.account.model;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;
    private String customerId;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
