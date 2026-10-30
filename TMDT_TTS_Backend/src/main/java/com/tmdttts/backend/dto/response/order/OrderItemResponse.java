package com.tmdttts.backend.dto.response.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, String productName, String sku, String variant,
        BigDecimal unitPrice, int quantity, BigDecimal lineTotal) {}
