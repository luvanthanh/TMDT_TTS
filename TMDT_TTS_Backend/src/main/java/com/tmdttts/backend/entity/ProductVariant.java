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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "product_variants")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_variant_id")
    private Long productVariantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_product_id", nullable = false)
    private Product productVariantProduct;

    @Column(name = "product_variant_sku", nullable = false, unique = true, length = 100)
    private String productVariantSku;

    @Column(name = "product_variant_color", length = 80)
    private String productVariantColor;

    @Column(name = "product_variant_size", length = 80)
    private String productVariantSize;

    @Column(name = "product_variant_price_override", precision = 12, scale = 2)
    private BigDecimal productVariantPriceOverride;

    @Column(name = "product_variant_stock_quantity", nullable = false)
    private int productVariantStockQuantity;

    @Column(name = "product_variant_status", nullable = false, length = 20)
    private String productVariantStatus = "ACTIVE";

    @Column(name = "product_variant_created_at", nullable = false)
    private OffsetDateTime productVariantCreatedAt;

    @PrePersist
    void prePersist() {
        if (productVariantCreatedAt == null) {
            productVariantCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getProductVariantId() { return productVariantId; }
    public Product getProductVariantProduct() { return productVariantProduct; }
    public void setProductVariantProduct(Product productVariantProduct) { this.productVariantProduct = productVariantProduct; }
    public String getProductVariantSku() { return productVariantSku; }
    public void setProductVariantSku(String productVariantSku) { this.productVariantSku = productVariantSku; }
    public String getProductVariantColor() { return productVariantColor; }
    public void setProductVariantColor(String productVariantColor) { this.productVariantColor = productVariantColor; }
    public String getProductVariantSize() { return productVariantSize; }
    public void setProductVariantSize(String productVariantSize) { this.productVariantSize = productVariantSize; }
    public BigDecimal getProductVariantPriceOverride() { return productVariantPriceOverride; }
    public void setProductVariantPriceOverride(BigDecimal productVariantPriceOverride) { this.productVariantPriceOverride = productVariantPriceOverride; }
    public int getProductVariantStockQuantity() { return productVariantStockQuantity; }
    public void setProductVariantStockQuantity(int productVariantStockQuantity) { this.productVariantStockQuantity = productVariantStockQuantity; }
    public String getProductVariantStatus() { return productVariantStatus; }
    public void setProductVariantStatus(String productVariantStatus) { this.productVariantStatus = productVariantStatus; }
    public OffsetDateTime getProductVariantCreatedAt() { return productVariantCreatedAt; }
}
