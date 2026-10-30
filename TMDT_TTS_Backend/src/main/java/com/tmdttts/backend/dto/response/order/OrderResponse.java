package com.tmdttts.backend.dto.response.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(Long id, String code, String status, String paymentMethod, String paymentStatus,
        BigDecimal subtotal, BigDecimal discount, BigDecimal shippingFee, BigDecimal total,
        String recipientName, String phone, String shippingAddress, OffsetDateTime createdAt,
        List<OrderItemResponse> items) {}
