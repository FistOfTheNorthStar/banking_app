package com.banking.user.service;

import com.banking.user.client.AccountServiceClient;
import com.banking.user.client.TransactionServiceClient;
import com.banking.user.dto.AccountDTO;
import com.banking.user.dto.TransactionDTO;
import com.banking.user.model.User;
import com.banking.user.model.UserAccountInfo;
import com.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountServiceClient accountServiceClient;
    private final TransactionServiceClient transactionServiceClient;

    public UserAccountInfo getUserAccountInfo(String userId) {
        // Get user information
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get account information
        AccountDTO account = accountServiceClient.getAccountByCustomerId(userId);

        // Get transactions
        List<TransactionDTO> transactions = transactionServiceClient
                .getTransactionsByAccountId(account.getId().toString());

        // Build and return the aggregated information
        return UserAccountInfo.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .balance(account.getBalance())
                .transactions(transactions)
                .build();
    }
}
