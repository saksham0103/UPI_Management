package com.tcs.services;

import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.AccountBalanceResponseDto;

public interface AdminAccountService {

    AccountBalanceResponseDto createAccount(AccountCreateRequestDto request);

    void freezeAccount(Long accountId);

    void closeAccount(Long accountId);
}
