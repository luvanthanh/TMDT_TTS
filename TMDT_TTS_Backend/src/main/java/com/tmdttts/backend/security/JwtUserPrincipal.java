package com.tmdttts.backend.security;

public record JwtUserPrincipal(Long userId, String role) {}
