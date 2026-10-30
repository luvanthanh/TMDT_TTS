package com.tmdttts.backend.dto.response.cart;

import java.math.BigDecimal;

public record CartItemResponse(Long cartItemId, Long variantId, Long productId, String productName,
        String imageUrl, String sku, String color, String size, BigDecimal unitPrice,
        int quantity, int stock, BigDecimal lineTotal) {}
