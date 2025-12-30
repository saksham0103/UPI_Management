package com.tcs.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tcs.dto.AdminResetPasswordRequestDto;
import com.tcs.dto.AdminUpdateContactRequestDto;
import com.tcs.dto.AdminUpdateUserProfileDto;
import com.tcs.dto.AdminUserResponseDto;

public interface AdminUserService {
	
	Page<AdminUserResponseDto> listUsers(Pageable pageable);

    void resetPassword(AdminResetPasswordRequestDto request);

    void updateUserContact(AdminUpdateContactRequestDto request);
    
    void updateUserProfile(AdminUpdateUserProfileDto request);

}
