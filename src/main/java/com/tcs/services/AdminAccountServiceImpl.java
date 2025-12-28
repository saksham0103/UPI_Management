package com.tcs.services;

import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.entity.Account;
import com.tcs.entity.User;
import com.tcs.enums.AccountStatus;
import com.tcs.enums.AccountType;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import com.tcs.services.AdminAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AdminAccountServiceImpl(AccountRepository accountRepository,
                                   UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountBalanceResponseDto createAccount(AccountCreateRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setIfsc("UPI000123");
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(request.getInitialDeposit()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setUser(user);

        accountRepository.save(account);

        AccountBalanceResponseDto dto = new AccountBalanceResponseDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance().doubleValue());
        dto.setAccountType(account.getAccountType().name());

        return dto;
    }

    @Override
    public void freezeAccount(Long accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setStatus(AccountStatus.FROZEN);
        accountRepository.save(acc);
    }

    @Override
    public void closeAccount(Long accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setStatus(AccountStatus.CLOSED);
        accountRepository.save(acc);
    }
}
