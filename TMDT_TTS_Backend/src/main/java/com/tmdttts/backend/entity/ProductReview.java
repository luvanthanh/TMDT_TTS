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
@Table(name = "product_reviews")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id")
    private Long productReviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_review_product_id", nullable = false)
    private Product productReviewProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_review_user_id", nullable = false)
    private User productReviewUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_review_order_item_id")
    private OrderItem productReviewOrderItem;

    @Column(name = "product_review_rating", nullable = false)
    private short productReviewRating;

    @Column(name = "product_review_title", length = 180)
    private String productReviewTitle;

    @Column(name = "product_review_content", columnDefinition = "text")
    private String productReviewContent;

    @Column(name = "product_review_status", nullable = false, length = 20)
    private String productReviewStatus = "PENDING";

    @Column(name = "product_review_created_at", nullable = false)
    private OffsetDateTime productReviewCreatedAt;

    @PrePersist
    void prePersist() {
        if (productReviewCreatedAt == null) {
            productReviewCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getProductReviewId() { return productReviewId; }
    public Product getProductReviewProduct() { return productReviewProduct; }
    public void setProductReviewProduct(Product productReviewProduct) { this.productReviewProduct = productReviewProduct; }
    public User getProductReviewUser() { return productReviewUser; }
    public void setProductReviewUser(User productReviewUser) { this.productReviewUser = productReviewUser; }
    public OrderItem getProductReviewOrderItem() { return productReviewOrderItem; }
    public void setProductReviewOrderItem(OrderItem productReviewOrderItem) { this.productReviewOrderItem = productReviewOrderItem; }
    public short getProductReviewRating() { return productReviewRating; }
    public void setProductReviewRating(short productReviewRating) { this.productReviewRating = productReviewRating; }
    public String getProductReviewTitle() { return productReviewTitle; }
    public void setProductReviewTitle(String productReviewTitle) { this.productReviewTitle = productReviewTitle; }
    public String getProductReviewContent() { return productReviewContent; }
    public void setProductReviewContent(String productReviewContent) { this.productReviewContent = productReviewContent; }
    public String getProductReviewStatus() { return productReviewStatus; }
    public void setProductReviewStatus(String productReviewStatus) { this.productReviewStatus = productReviewStatus; }
    public OffsetDateTime getProductReviewCreatedAt() { return productReviewCreatedAt; }
}
