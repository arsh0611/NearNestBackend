package com.nearnest.user_service.controller;

import com.nearnest.user_service.dto.ApiResponse;
import com.nearnest.user_service.dto.SignupRequest;
import com.nearnest.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        userService.registerUser(signupRequest);
        return ResponseEntity.status(201)
                .body(new ApiResponse(true, "Signup successful"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateUser(@RequestBody SignupRequest req) {
        ApiResponse response = userService.validateUser(req.getUsername(), req.getPassword());

        if (!response.isSuccess()) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
