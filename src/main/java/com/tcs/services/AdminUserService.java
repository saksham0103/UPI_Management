package com.tcs.services;

import com.tcs.dto.AdminResetPasswordRequestDto;
import com.tcs.dto.AdminUpdateContactRequestDto;

public interface AdminUserService {

    void resetPassword(AdminResetPasswordRequestDto request);

    void updateUserContact(AdminUpdateContactRequestDto request);
}
