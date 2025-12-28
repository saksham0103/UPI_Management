package com.tcs.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.ApiResponseDto;
import com.tcs.dto.AuthResponseDto;
import com.tcs.dto.LoginRequestDto;
import com.tcs.dto.RegisterRequestDto;
import com.tcs.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ REGISTER (NO TOKEN)
    @PostMapping("/register")
    public ApiResponseDto register(@RequestBody RegisterRequestDto request) {
        authService.register(request);
        return new ApiResponseDto(true, "User registered successfully");
    }

    // ✅ LOGIN (TOKEN ISSUED HERE)
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
}
