package com.banking.user.model;

import com.banking.user.dto.TransactionDTO;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccountInfo {
    private String name;
    private String surname;
    private BigDecimal balance;
    private List<TransactionDTO> transactions;
}