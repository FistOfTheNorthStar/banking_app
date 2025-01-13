package com.banking.user.client;

import com.banking.user.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface AccountServiceClient {
    @GetMapping("/api/accounts/customer/{customerId}")
    AccountDTO getAccountByCustomerId(@PathVariable String customerId);
}
