package com.banking.transaction.model;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    private UUID id;
    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String type;
}