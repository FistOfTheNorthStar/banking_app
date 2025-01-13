package com.banking.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private UUID id;
    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String type;
}