package com.tcs.controller;

import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.ApiResponseDto;
import com.tcs.services.AdminAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/account")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;

    public AdminAccountController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @PostMapping("/create")
    public AccountBalanceResponseDto createAccount(
            @RequestBody AccountCreateRequestDto request) {

        return adminAccountService.createAccount(request);
    }

    @PutMapping("/freeze/{accountId}")
    public ApiResponseDto freezeAccount(@PathVariable Long accountId) {

        adminAccountService.freezeAccount(accountId);
        return new ApiResponseDto(true, "Account frozen successfully");
    }

    @PutMapping("/close/{accountId}")
    public ApiResponseDto closeAccount(@PathVariable Long accountId) {

        adminAccountService.closeAccount(accountId);
        return new ApiResponseDto(true, "Account closed successfully");
    }
}
