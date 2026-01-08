package com.tcs.services;

import com.tcs.entity.Otp;
import com.tcs.entity.User;
import com.tcs.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService{

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public void generateOtp(User user) {
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtpCode(String.valueOf(100000 + new Random().nextInt(900000)));
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);

        System.out.println("OTP = " + otp.getOtpCode()); // simulate SMS
    }

    public void validateOtp(Long userId, String otp) {
        Otp latest = otpRepository
                .findTopByUserIdOrderByExpiryTimeDesc(userId)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (latest.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!latest.getOtpCode().equals(otp))
            throw new RuntimeException("Invalid OTP");
    }

}

