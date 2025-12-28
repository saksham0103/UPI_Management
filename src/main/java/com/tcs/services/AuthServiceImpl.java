package com.tcs.services;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.dto.AuthResponseDto;
import com.tcs.dto.LoginRequestDto;
import com.tcs.dto.RegisterRequestDto;
import com.tcs.entity.Role;
import com.tcs.entity.User;
import com.tcs.exception.BadRequestException;
import com.tcs.repository.RoleRepository;
import com.tcs.repository.UserRepository;
import com.tcs.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ REGISTER → SAVE USER + DEFAULT ROLE
    @Override
    public void register(RegisterRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new BadRequestException("Phone already exists");
        }

        // 🔹 Fetch ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found in database")
                );

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        // 🔑 Assign default role
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
    }

    // ✅ LOGIN → VERIFY → GENERATE TOKEN
    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getUsername())
                .orElseGet(() ->
                        userRepository.findByPhone(request.getUsername())
                                .orElseThrow(() ->
                                        new BadRequestException("Invalid username or password")
                                )
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        // ✅ Extract role (default ROLE_USER)
        String role = user.getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN"))
                ? "ROLE_ADMIN"
                : "ROLE_USER";

        String token = jwtUtil.generateToken(user.getEmail(), role);

        return new AuthResponseDto(
                token,
                role,
                "Login successful"
        );
    }

}
