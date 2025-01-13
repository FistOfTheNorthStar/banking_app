package com.banking.account.controller;

import com.banking.account.model.Account;
import com.banking.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
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

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {AccountControllerTest.TestConfig.class})
class AccountControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public AccountService accountService() {
            return mock(AccountService.class);
        }

        @Bean
        public AccountController accountController(AccountService accountService) {
            return new AccountController(accountService);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Test
    void createAccount_Success() throws Exception {
        String customerId = "1";
        BigDecimal initialCredit = BigDecimal.ZERO;
        UUID accountId = UUID.randomUUID();
        
        Account account = Account.builder()
                .id(accountId)
                .customerId(customerId)
                .balance(initialCredit)
                .build();

        when(accountService.createAccount(eq(customerId), eq(initialCredit))).thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                .param("customerId", customerId)
                .param("initialCredit", initialCredit.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId.toString()))
                .andExpect(jsonPath("$.customerId").value(customerId));
    }

    @Test
    void getAccount_Success() throws Exception {
        UUID accountId = UUID.randomUUID();
        Account account = Account.builder()
                .id(accountId)
                .customerId("1")
                .balance(BigDecimal.ZERO)
                .build();

        when(accountService.getAccount(accountId)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId.toString()))
                .andExpect(jsonPath("$.customerId").value("1"));
    }
}