package com.banking.user.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {
    private UUID id;
    private String customerId;
    private BigDecimal balance;
}