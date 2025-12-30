package com.tcs.dto;

public class AdminAccountResponseDto {

    private Long id;
    private String accountNumber;
    private String accountType;
    private String status;
    private Double balance;

    // 🔥 NEW
    private String userName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
