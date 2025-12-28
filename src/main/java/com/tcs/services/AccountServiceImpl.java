package com.tcs.services;

import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.entity.Account;
import com.tcs.entity.User;
import com.tcs.enums.AccountType;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountBalanceResponseDto getAccountBalanceByUsername(
            String username,
            AccountType accountType) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository
                .findByUserAndAccountType(user, accountType)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountBalanceResponseDto dto = new AccountBalanceResponseDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance().doubleValue());
        dto.setAccountType(account.getAccountType().name());

        return dto;
    }
}
