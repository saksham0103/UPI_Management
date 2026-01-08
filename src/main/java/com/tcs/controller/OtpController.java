package com.tcs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.tcs.dto.ApiResponseDto;
import com.tcs.entity.User;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import com.tcs.services.OtpService;

@RestController
@RequestMapping("/api/user/otp")
public class OtpController {

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    
    

    public OtpController(OtpService otpService, UserRepository userRepository, AccountRepository accountRepository) {
		super();
		this.otpService = otpService;
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
	}



	@PostMapping("/generate")
    public ApiResponseDto generateOtp(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!accountRepository.existsByUserId(user.getId()))
            throw new RuntimeException("User has no account");

        otpService.generateOtp(user);
        return new ApiResponseDto(true, "OTP generated");
    }
}
