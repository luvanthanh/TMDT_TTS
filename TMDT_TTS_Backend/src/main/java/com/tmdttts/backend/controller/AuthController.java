package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service=service; }
    @PostMapping("/register") public Responses.Auth register(@Valid @RequestBody Requests.Register request) { return service.register(request); }
    @PostMapping("/login") public Responses.Auth login(@Valid @RequestBody Requests.Login request) { return service.login(request); }
}
