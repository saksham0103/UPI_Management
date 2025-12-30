package com.tcs.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.enums.AccountStatus;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AdminStatsController(
            UserRepository userRepository,
            AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/users")
    public Map<String, Long> totalUsers() {
        return Map.of("count", userRepository.count());
    }

    @GetMapping("/accounts")
    public Map<String, Long> totalAccounts() {
        return Map.of("count", accountRepository.count());
    }

    @GetMapping("/frozen-accounts")
    public Map<String, Long> frozenAccounts() {
        return Map.of(
            "count",
            accountRepository.countByStatus(AccountStatus.FROZEN)
        );
    }

    @GetMapping("/closed-accounts")
    public Map<String, Long> closedAccounts() {
        return Map.of(
            "count",
            accountRepository.countByStatus(AccountStatus.CLOSED)
        );
    }
}
