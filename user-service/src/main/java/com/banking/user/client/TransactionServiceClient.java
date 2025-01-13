package com.banking.user.client;

import com.banking.user.dto.TransactionDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "transaction-service", url = "${transaction.service.url}")
public interface TransactionServiceClient {
    @GetMapping("/api/transactions/account/{accountId}")
    List<TransactionDTO> getTransactionsByAccountId(@PathVariable String accountId);
}