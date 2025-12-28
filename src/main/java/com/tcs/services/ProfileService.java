package com.tcs.services;

import com.tcs.dto.ProfileUpdateRequestDto;
import com.tcs.dto.UserProfileResponseDto;

public interface ProfileService {

    UserProfileResponseDto getProfileByUsername(String username);

    UserProfileResponseDto updateProfile(
            String username,
            ProfileUpdateRequestDto request
    );
}
