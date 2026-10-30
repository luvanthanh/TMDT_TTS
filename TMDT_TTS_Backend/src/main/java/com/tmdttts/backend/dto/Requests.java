package com.tmdttts.backend.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public final class Requests {
    private Requests() {}

    public record Register(@NotBlank @Size(max=150) String name, @NotBlank @Email @Size(max=255) String email,
                           @Size(max=30) String phone, @NotBlank @Size(min=8, max=72) String password) {}
    public record Login(@NotBlank @Email String email, @NotBlank String password) {}
    public record CartItem(@NotNull @Positive Long variantId, @Min(1) @Max(99) int quantity) {}
    public record Quantity(@Min(1) @Max(99) int quantity) {}
    public record Checkout(@NotBlank @Size(max=150) String recipientName, @NotBlank @Size(max=30) String phone,
                           @NotBlank @Size(max=255) String addressDetail, @Size(max=100) String ward,
                           @Size(max=100) String district, @NotBlank @Size(max=100) String city,
                           @Size(max=20) String postalCode, @Size(max=1000) String note,
                           @NotBlank String paymentMethod, String couponCode) {}
    public record Address(@NotBlank @Size(max=150) String recipientName, @NotBlank @Size(max=30) String phone,
                          @NotBlank @Size(max=255) String detail, @Size(max=100) String ward,
                          @Size(max=100) String district, @NotBlank @Size(max=100) String city,
                          @Size(max=20) String postalCode, boolean defaultAddress) {}
    public record Profile(@NotBlank @Size(max=150) String name, @Size(max=30) String phone) {}
    public record Contact(@NotBlank @Size(max=150) String fullName, @NotBlank @Email @Size(max=255) String email,
                          @Size(max=30) String phone, @Size(max=200) String subject,
                          @NotBlank @Size(max=10000) String content) {}
}
