package com.tmdttts.backend.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckoutRequest(@NotBlank @Size(max=150) String recipientName,
        @NotBlank @Size(max=30) String phone, @NotBlank @Size(max=255) String addressDetail,
        @Size(max=100) String ward, @Size(max=100) String district,
        @NotBlank @Size(max=100) String city, @Size(max=20) String postalCode,
        @Size(max=1000) String note, @NotBlank String paymentMethod, String couponCode) {}
