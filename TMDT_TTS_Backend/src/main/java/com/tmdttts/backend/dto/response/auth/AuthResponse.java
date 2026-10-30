package com.tmdttts.backend.dto.response.auth;

public record AuthResponse(Long userId, String name, String email, String role, String token, String tokenType) {}
