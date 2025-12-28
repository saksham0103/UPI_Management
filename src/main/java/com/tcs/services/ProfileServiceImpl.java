package com.tcs.services;

import com.tcs.dto.ProfileUpdateRequestDto;
import com.tcs.dto.UserProfileResponseDto;
import com.tcs.entity.User;
import com.tcs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ GET PROFILE USING USERNAME (JWT)
    @Override
    public UserProfileResponseDto getProfileByUsername(String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return mapToDto(user);
    }

    // ✅ UPDATE PROFILE USING USERNAME (JWT)
    @Override
    public UserProfileResponseDto updateProfile(
            String username,
            ProfileUpdateRequestDto request) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Update allowed fields
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        if (request.getPassword() != null &&
            !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        userRepository.save(user);
        return mapToDto(user);
    }

    // ✅ Helper mapper
    private UserProfileResponseDto mapToDto(User user) {

        UserProfileResponseDto dto = new UserProfileResponseDto();
        dto.setUserId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
