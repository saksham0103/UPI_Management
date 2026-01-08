package com.tcs.services;

import com.tcs.entity.User;

public interface OtpService {

    void generateOtp(User user);

    void validateOtp(Long userId, String otp);
}
