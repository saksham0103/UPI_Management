package com.tcs.dto;

public class AccountCreateRequestDto {

    private Long userId;
    private Double initialDeposit;

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
}
