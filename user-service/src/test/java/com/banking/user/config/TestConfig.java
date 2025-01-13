package com.banking.user.config;

import static org.mockito.Mockito.mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.banking.user.client.AccountServiceClient;
import com.banking.user.client.TransactionServiceClient;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public AccountServiceClient accountServiceClient() {
        return mock(AccountServiceClient.class);
    }
    
    @Bean
    @Primary
    public TransactionServiceClient transactionServiceClient() {
        return mock(TransactionServiceClient.class);
    }
}
