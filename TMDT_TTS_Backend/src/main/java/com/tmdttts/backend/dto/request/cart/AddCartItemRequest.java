package com.tmdttts.backend.dto.request.cart;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddCartItemRequest(@NotNull @Positive Long variantId, @Min(1) @Max(99) int quantity) {}
