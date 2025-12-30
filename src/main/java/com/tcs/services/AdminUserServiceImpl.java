package com.tcs.services;

import com.tcs.dto.AdminResetPasswordRequestDto;
import com.tcs.dto.AdminUpdateContactRequestDto;
import com.tcs.dto.AdminUpdateUserProfileDto;
import com.tcs.dto.AdminUserResponseDto;
import com.tcs.entity.User;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.UserRepository;
import com.tcs.services.AdminUserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserServiceImpl(UserRepository userRepository,
                                PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository=accountRepository;
    }

    @Override
    public void resetPassword(AdminResetPasswordRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUserContact(AdminUpdateContactRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        userRepository.save(user);
    }

    private AdminUserResponseDto map(User user) {
        AdminUserResponseDto dto = new AdminUserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setActive(user.isActive());
        
        boolean hasAccount = accountRepository.existsByUserId(user.getId());
        dto.setHasAccount(hasAccount);
        return dto;
    }

    @Override
    public Page<AdminUserResponseDto> listUsers(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(this::map);
    }
    
    @Override
    public void updateUserProfile(AdminUpdateUserProfileDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);
    }

    
    
}
