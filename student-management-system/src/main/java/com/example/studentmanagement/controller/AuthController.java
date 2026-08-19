package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public AuthResponse adminLogin(@Valid @RequestBody LoginRequest request) {
        return authService.adminLogin(request);
    }

    @PostMapping("/student/verify")
    public AuthResponse verifyStudent(@Valid @RequestBody StudentVerificationRequest request) {
        return authService.studentVerify(request);
    }
}
