package com.nearnest.user_service.service;


import com.nearnest.user_service.dto.ApiResponse;
import com.nearnest.user_service.dto.SignupRequest;
import com.nearnest.user_service.entity.User;
import com.nearnest.user_service.exception.ResourceConflictException;
import com.nearnest.user_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User registerUser(SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ResourceConflictException("username already exists");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResourceConflictException("email already exists");
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .memberSince(OffsetDateTime.now())
                .build();

        return userRepository.save(user);
    }
    public ApiResponse validateUser(String username, String rawPassword) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "Invalid username or password");
        }

        var user = userOpt.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new ApiResponse(false, "Invalid username or password");
        }

        return new ApiResponse(true, "Login successful");
    }
}

