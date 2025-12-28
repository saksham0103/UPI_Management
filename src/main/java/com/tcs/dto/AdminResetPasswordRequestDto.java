package com.tcs.dto;

public class AdminResetPasswordRequestDto {

    private Long userId;
    private String newPassword;

    public AdminResetPasswordRequestDto() {}

    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
