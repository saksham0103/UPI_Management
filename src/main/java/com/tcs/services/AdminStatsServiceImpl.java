package com.tcs.services;

import com.tcs.enums.AccountStatus;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminStatsServiceImpl implements AdminStatsService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AdminStatsServiceImpl(UserRepository userRepository,
                                 AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Long totalUsers() {
        return userRepository.count();
    }

    @Override
    public Long totalAccounts() {
        return accountRepository.count();
    }

    @Override
    public Long frozenAccounts() {
        return accountRepository.countByStatus(AccountStatus.FROZEN);
    }
}
