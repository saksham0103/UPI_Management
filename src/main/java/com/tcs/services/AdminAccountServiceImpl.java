package com.tcs.services;

import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.AdminAccountResponseDto;
import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.entity.Account;
import com.tcs.entity.User;
import com.tcs.enums.AccountStatus;
import com.tcs.enums.AccountType;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import com.tcs.services.AdminAccountService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        // 🔒 Optional: prevent duplicate account
        if (accountRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("User already has an account");
        }

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setIfsc("UPI000123");

        account.setAccountType(
            AccountType.valueOf(request.getAccountType())
        );

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


    private AdminAccountResponseDto map(Account acc) {
        AdminAccountResponseDto dto = new AdminAccountResponseDto();
        dto.setId(acc.getId()); // 🔥 FIX
        dto.setAccountNumber(acc.getAccountNumber());
        dto.setAccountType(acc.getAccountType().name());
        dto.setStatus(acc.getStatus().name());
        dto.setBalance(acc.getBalance().doubleValue());
        dto.setUserName(acc.getUser().getName());
        return dto;
    }

    @Override
    public Page<AdminAccountResponseDto> listAccounts(
            String search,
            Pageable pageable) {

        Page<Account> page = (search == null || search.isBlank())
                ? accountRepository.findAll(pageable)
                : accountRepository.findByAccountNumberContainingIgnoreCase(
                        search, pageable);

        return page.map(this::map);
    }

    @Override
    public Page<AdminAccountResponseDto> listAccountsByStatus(
            String status,
            Pageable pageable) {

        return accountRepository
                .findByStatus(AccountStatus.valueOf(status), pageable)
                .map(this::map);
    }

    @Override
    public void freezeAccount(Long id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setStatus(AccountStatus.FROZEN);
        accountRepository.save(acc);
    }

    @Override
    public void activateAccount(Long id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(acc);
    }

    @Override
    public void closeAccount(Long id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setStatus(AccountStatus.CLOSED);
        accountRepository.save(acc);
    }


    
}
