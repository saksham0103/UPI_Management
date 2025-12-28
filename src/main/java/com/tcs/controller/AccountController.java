package com.tcs.controller;

import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.enums.AccountType;
import com.tcs.services.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public AccountBalanceResponseDto getBalance(
            Authentication authentication,
            @RequestParam AccountType accountType) {

        String username = authentication.getName(); // email from JWT
        return accountService.getAccountBalanceByUsername(username, accountType);
    }
}
