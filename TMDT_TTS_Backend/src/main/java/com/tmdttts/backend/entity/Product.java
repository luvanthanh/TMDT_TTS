package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private Category productCategory;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "product_slug", nullable = false, unique = true, length = 280)
    private String productSlug;

    @Column(name = "product_description", columnDefinition = "text")
    private String productDescription;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "product_sale_price", precision = 12, scale = 2)
    private BigDecimal productSalePrice;

    @Column(name = "product_status", nullable = false, length = 20)
    private String productStatus = "DRAFT";

    @Column(name = "product_is_featured", nullable = false)
    private boolean productIsFeatured;

    @Column(name = "product_is_flash_sale", nullable = false)
    private boolean productIsFlashSale;

    @Column(name = "product_created_at", nullable = false)
    private OffsetDateTime productCreatedAt;

    @Column(name = "product_updated_at", nullable = false)
    private OffsetDateTime productUpdatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (productCreatedAt == null) {
            productCreatedAt = now;
        }
        if (productUpdatedAt == null) {
            productUpdatedAt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        productUpdatedAt = OffsetDateTime.now();
    }

    public Long getProductId() { return productId; }
    public Category getProductCategory() { return productCategory; }
    public void setProductCategory(Category productCategory) { this.productCategory = productCategory; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductSlug() { return productSlug; }
    public void setProductSlug(String productSlug) { this.productSlug = productSlug; }
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }
    public BigDecimal getProductSalePrice() { return productSalePrice; }
    public void setProductSalePrice(BigDecimal productSalePrice) { this.productSalePrice = productSalePrice; }
    public String getProductStatus() { return productStatus; }
    public void setProductStatus(String productStatus) { this.productStatus = productStatus; }
    public boolean isProductIsFeatured() { return productIsFeatured; }
    public void setProductIsFeatured(boolean productIsFeatured) { this.productIsFeatured = productIsFeatured; }
    public boolean isProductIsFlashSale() { return productIsFlashSale; }
    public void setProductIsFlashSale(boolean productIsFlashSale) { this.productIsFlashSale = productIsFlashSale; }
    public OffsetDateTime getProductCreatedAt() { return productCreatedAt; }
    public OffsetDateTime getProductUpdatedAt() { return productUpdatedAt; }
}
