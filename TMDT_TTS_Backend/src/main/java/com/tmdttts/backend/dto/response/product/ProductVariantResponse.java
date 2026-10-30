package com.tmdttts.backend.dto.response.product;

import java.math.BigDecimal;

public record ProductVariantResponse(Long id, String sku, String color, String size, BigDecimal price, int stock) {}
