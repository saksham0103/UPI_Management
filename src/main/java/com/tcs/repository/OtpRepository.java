package com.tcs.repository;

import com.tcs.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUserId(Long userId);

    Optional<Otp> findByOtpCode(String otpCode);
}
