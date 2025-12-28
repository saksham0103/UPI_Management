package com.tcs.dto;

public class AdminUpdateContactRequestDto {

    private Long userId;
    private String email;
    private String phone;

    public AdminUpdateContactRequestDto() {}

    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
