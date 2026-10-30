package com.tmdttts.backend.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(@NotBlank @Size(max=150) String recipientName,
        @NotBlank @Size(max=30) String phone, @NotBlank @Size(max=255) String detail,
        @Size(max=100) String ward, @Size(max=100) String district,
        @NotBlank @Size(max=100) String city, @Size(max=20) String postalCode,
        boolean defaultAddress) {}
