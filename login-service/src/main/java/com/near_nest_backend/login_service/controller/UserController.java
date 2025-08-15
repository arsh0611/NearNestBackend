package com.near_nest_backend.login_service.controller;

import com.near_nest_backend.login_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean success = userService.login(username, password);
        if (success) {
            return ResponseEntity.ok("Login Success");
        } else {
            return ResponseEntity.status(401).body("Login Failed");
        }
    }
}