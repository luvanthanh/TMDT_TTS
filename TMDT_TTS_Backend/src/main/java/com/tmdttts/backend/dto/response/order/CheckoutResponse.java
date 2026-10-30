package com.tmdttts.backend.dto.response.order;

public record CheckoutResponse(OrderResponse order, String paymentUrl) {}
