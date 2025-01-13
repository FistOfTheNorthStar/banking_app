package com.banking.account.client;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service", url = "${transaction.service.url:http://localhost:8071}")
public interface TransactionServiceClient {
    @PostMapping("/api/transactions")
    void createTransaction(@RequestParam UUID accountId, @RequestParam BigDecimal amount);
}