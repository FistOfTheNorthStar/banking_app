package com.banking.account.config;

import com.banking.account.client.TransactionServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public TransactionServiceClient transactionServiceClient() {
        return new TransactionServiceClient() {
            @Override
            public void createTransaction(java.util.UUID accountId, java.math.BigDecimal amount) {
            }
        };
    }
}