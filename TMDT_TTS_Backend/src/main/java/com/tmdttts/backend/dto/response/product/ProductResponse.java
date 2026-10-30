package com.tmdttts.backend.dto.response.product;

import com.tmdttts.backend.dto.response.category.CategoryResponse;
import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(Long id, String name, String slug, String description, BigDecimal price,
        BigDecimal salePrice, String status, boolean featured, boolean flashSale,
        CategoryResponse category, List<ProductImageResponse> images, List<ProductVariantResponse> variants) {}
