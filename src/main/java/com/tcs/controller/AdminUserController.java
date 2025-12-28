package com.tcs.controller;

import com.tcs.dto.AdminResetPasswordRequestDto;
import com.tcs.dto.AdminUpdateContactRequestDto;
import com.tcs.dto.ApiResponseDto;
import com.tcs.services.AdminUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PutMapping("/reset-password")
    public ApiResponseDto resetPassword(
            @RequestBody AdminResetPasswordRequestDto request) {

        adminUserService.resetPassword(request);
        return new ApiResponseDto(true, "Password reset successful");
    }

    @PutMapping("/update-contact")
    public ApiResponseDto updateContact(
            @RequestBody AdminUpdateContactRequestDto request) {

        adminUserService.updateUserContact(request);
        return new ApiResponseDto(true, "User contact updated");
    }
}
