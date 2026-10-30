package com.tmdttts.backend.dto.request.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(@NotBlank @Size(max=150) String fullName,
        @NotBlank @Email @Size(max=255) String email, @Size(max=30) String phone,
        @Size(max=200) String subject, @NotBlank @Size(max=10000) String content) {}
