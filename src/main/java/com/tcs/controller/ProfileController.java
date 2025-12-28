package com.tcs.controller;

import com.tcs.dto.ProfileUpdateRequestDto;
import com.tcs.dto.UserProfileResponseDto;
import com.tcs.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // ✅ GET PROFILE (from JWT)
    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getProfile(
            Authentication authentication) {

        String username = authentication.getName(); // email / username from JWT
        return ResponseEntity.ok(
                profileService.getProfileByUsername(username)
        );
    }

    // ✅ UPDATE PROFILE (from JWT)
    @PutMapping
    public ResponseEntity<UserProfileResponseDto> updateProfile(
            Authentication authentication,
            @RequestBody ProfileUpdateRequestDto request) {

        String username = authentication.getName();
        return ResponseEntity.ok(
                profileService.updateProfile(username, request)
        );
    }
}
