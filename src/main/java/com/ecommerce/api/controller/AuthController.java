package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ApiResponse;
import com.ecommerce.api.dto.AuthResponse;
import com.ecommerce.api.dto.LoginRequest;
import com.ecommerce.api.dto.RegisterRequest;
import com.ecommerce.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse response = new ApiResponse(true, "User registered successfully", authResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse response = new ApiResponse(true, "Login successful", authResponse);
        return ResponseEntity.ok(response);
    }
}
