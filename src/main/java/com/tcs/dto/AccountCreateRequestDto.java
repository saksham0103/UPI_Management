package com.tcs.dto;

public class AccountCreateRequestDto {

    private Long userId;
    private Double initialDeposit;

    // 🔥 ADD THIS
    private String accountType;

    public AccountCreateRequestDto() {}

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }
    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
