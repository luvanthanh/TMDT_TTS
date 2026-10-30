package com.tmdttts.backend.dto.response.product;

public record ProductImageResponse(String url, String altText, boolean primary, int sortOrder) {}
