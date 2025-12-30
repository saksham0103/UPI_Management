package com.tcs.controller;

import com.tcs.dto.AccountBalanceResponseDto;
import com.tcs.dto.AccountCreateRequestDto;
import com.tcs.dto.AdminAccountResponseDto;
import com.tcs.dto.ApiResponseDto;
import com.tcs.entity.Account;
import com.tcs.enums.AccountStatus;
import com.tcs.repository.AccountRepository;
import com.tcs.services.AdminAccountService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/account")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;
    private final AccountRepository accountRepository;

    public AdminAccountController(AdminAccountService adminAccountService, AccountRepository accountRepository) {
        this.adminAccountService = adminAccountService;
        this.accountRepository=accountRepository;
    }

    @PostMapping("/create")
    public AccountBalanceResponseDto createAccount(
            @RequestBody AccountCreateRequestDto request) {

        return adminAccountService.createAccount(request);
    }

    @GetMapping("/list")
    public Page<AdminAccountResponseDto> list(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return adminAccountService.listAccounts(search, pageable);
    }

    @GetMapping("/status/{status}")
    public Page<AdminAccountResponseDto> listByStatus(
            @PathVariable String status,
            Pageable pageable) {
        return adminAccountService.listAccountsByStatus(status, pageable);
    }

    @PutMapping("/freeze/{id}")
    public ApiResponseDto freeze(@PathVariable Long id) {
    	adminAccountService.freezeAccount(id);
        return new ApiResponseDto(true, "Account frozen");
    }

    @PutMapping("/activate/{id}")
    public ApiResponseDto activate(@PathVariable Long id) {
    	adminAccountService.activateAccount(id);
        return new ApiResponseDto(true, "Account activated");
    }

    @PutMapping("/close/{id}")
    public ApiResponseDto close(@PathVariable Long id) {
    	adminAccountService.closeAccount(id);
        return new ApiResponseDto(true, "Account closed");
    }

    
}
