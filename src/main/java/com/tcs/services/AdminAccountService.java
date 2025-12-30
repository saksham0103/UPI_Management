package com.tcs.services;

import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.AdminAccountResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tcs.dto.AccountBalanceResponseDto;

public interface AdminAccountService {

    AccountBalanceResponseDto createAccount(AccountCreateRequestDto request);
    
    Page<AdminAccountResponseDto> listAccounts(String search, Pageable pageable);

    Page<AdminAccountResponseDto> listAccountsByStatus(
            String status,
            Pageable pageable
    );

    void freezeAccount(Long accountId);
    void closeAccount(Long accountId);
    void activateAccount(Long accountId);
    


    
}
