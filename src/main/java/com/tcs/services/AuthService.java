package com.tcs.services;

import com.tcs.dto.AuthResponseDto;
import com.tcs.dto.LoginRequestDto;
import com.tcs.dto.RegisterRequestDto;

public interface AuthService {

    void register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}

