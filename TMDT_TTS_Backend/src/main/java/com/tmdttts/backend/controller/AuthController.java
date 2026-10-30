package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.request.auth.RegisterRequest;
import com.tmdttts.backend.dto.request.auth.LoginRequest;
import com.tmdttts.backend.dto.response.auth.AuthResponse;

import com.tmdttts.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service=service; }
    @PostMapping("/register") public AuthResponse register(@Valid @RequestBody RegisterRequest request) { return service.register(request); }
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest request) { return service.login(request); }
}
