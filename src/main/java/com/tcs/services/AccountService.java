package com.tcs.services;

import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.enums.AccountType;

public interface AccountService {

    AccountBalanceResponseDto getAccountBalanceByUsername(
            String username,
            AccountType accountType
    );
}
