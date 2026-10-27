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
import java.time.OffsetDateTime;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_image_product_id", nullable = false)
    private Product productImageProduct;

    @Column(name = "product_image_url", nullable = false, columnDefinition = "text")
    private String productImageUrl;

    @Column(name = "product_image_alt_text", length = 255)
    private String productImageAltText;

    @Column(name = "product_image_is_primary", nullable = false)
    private boolean productImageIsPrimary;

    @Column(name = "product_image_sort_order", nullable = false)
    private int productImageSortOrder;

    @Column(name = "product_image_created_at", nullable = false)
    private OffsetDateTime productImageCreatedAt;

    @PrePersist
    void prePersist() {
        if (productImageCreatedAt == null) {
            productImageCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getProductImageId() { return productImageId; }
    public Product getProductImageProduct() { return productImageProduct; }
    public void setProductImageProduct(Product productImageProduct) { this.productImageProduct = productImageProduct; }
    public String getProductImageUrl() { return productImageUrl; }
    public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
    public String getProductImageAltText() { return productImageAltText; }
    public void setProductImageAltText(String productImageAltText) { this.productImageAltText = productImageAltText; }
    public boolean isProductImageIsPrimary() { return productImageIsPrimary; }
    public void setProductImageIsPrimary(boolean productImageIsPrimary) { this.productImageIsPrimary = productImageIsPrimary; }
    public int getProductImageSortOrder() { return productImageSortOrder; }
    public void setProductImageSortOrder(int productImageSortOrder) { this.productImageSortOrder = productImageSortOrder; }
    public OffsetDateTime getProductImageCreatedAt() { return productImageCreatedAt; }
}
