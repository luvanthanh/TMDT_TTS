package com.tmdttts.backend.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public final class Responses {
    private Responses() {}
    public record Auth(Long userId, String name, String email, String role, String token, String tokenType) {}
    public record User(Long id, String name, String email, String phone, String role) {}
    public record Category(Long id, String name, String slug, String description, Long parentId) {}
    public record Variant(Long id, String sku, String color, String size, BigDecimal price, int stock) {}
    public record Image(String url, String altText, boolean primary, int sortOrder) {}
    public record Product(Long id, String name, String slug, String description, BigDecimal price,
                          BigDecimal salePrice, String status, boolean featured, boolean flashSale,
                          Category category, List<Image> images, List<Variant> variants) {}
    public record CartLine(Long cartItemId, Long variantId, Long productId, String productName,
                           String imageUrl, String sku, String color, String size, BigDecimal unitPrice,
                           int quantity, int stock, BigDecimal lineTotal) {}
    public record Cart(List<CartLine> items, BigDecimal subtotal, int totalQuantity) {}
    public record Address(Long id, String recipientName, String phone, String detail, String ward,
                          String district, String city, String postalCode, boolean defaultAddress) {}
    public record OrderLine(Long id, String productName, String sku, String variant, BigDecimal unitPrice,
                            int quantity, BigDecimal lineTotal) {}
    public record Order(Long id, String code, String status, String paymentMethod, String paymentStatus,
                        BigDecimal subtotal, BigDecimal discount, BigDecimal shippingFee, BigDecimal total,
                        String recipientName, String phone, String shippingAddress,
                        OffsetDateTime createdAt, List<OrderLine> items) {}
    public record CheckoutResult(Order order, String paymentUrl) {}
    public record Message(String message) {}
}
