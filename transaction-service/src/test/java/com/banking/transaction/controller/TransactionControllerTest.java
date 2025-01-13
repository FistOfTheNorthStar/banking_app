package com.banking.transaction.controller;

import com.banking.transaction.model.Transaction;
import com.banking.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@ContextConfiguration(classes = {TransactionControllerTest.TestConfig.class})
class TransactionControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public TransactionService transactionService() {
            return mock(TransactionService.class);
        }

        @Bean
        public TransactionController transactionController(TransactionService transactionService) {
            return new TransactionController(transactionService);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @Test
    void createTransaction_Success() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);
        Transaction mockTransaction = Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build();

        when(transactionService.createTransaction(eq(accountId), eq(amount))).thenReturn(mockTransaction);

        mockMvc.perform(post("/api/transactions")
                .param("accountId", accountId.toString())
                .param("amount", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.amount").value(amount.doubleValue()));
    }

    @Test
    void getTransactionsByAccountId_Success() throws Exception {
        UUID accountId = UUID.randomUUID();
        List<Transaction> mockTransactions = List.of(
            Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(100))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build(),
            Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(50))
                .timestamp(LocalDateTime.now())
                .type("CREDIT")
                .build()
        );

        when(transactionService.getTransactionsByAccountId(eq(accountId))).thenReturn(mockTransactions);

        mockMvc.perform(get("/api/transactions/account/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountId").value(accountId.toString()))
                .andExpect(jsonPath("$[1].accountId").value(accountId.toString()));
    }
}
