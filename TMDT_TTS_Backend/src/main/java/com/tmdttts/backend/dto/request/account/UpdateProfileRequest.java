package com.tmdttts.backend.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(@NotBlank @Size(max=150) String name, @Size(max=30) String phone) {}
