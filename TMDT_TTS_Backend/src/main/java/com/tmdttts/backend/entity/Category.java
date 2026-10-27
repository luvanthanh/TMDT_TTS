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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @Column(name = "category_name", nullable = false, length = 120)
    private String categoryName;

    @Column(name = "category_slug", nullable = false, unique = true, length = 150)
    private String categorySlug;

    @Column(name = "category_description", columnDefinition = "text")
    private String categoryDescription;

    @Column(name = "category_status", nullable = false, length = 20)
    private String categoryStatus = "ACTIVE";

    @Column(name = "category_created_at", nullable = false)
    private OffsetDateTime categoryCreatedAt;

    @PrePersist
    void prePersist() {
        if (categoryCreatedAt == null) {
            categoryCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getCategoryId() { return categoryId; }
    public Category getCategoryParent() { return categoryParent; }
    public void setCategoryParent(Category categoryParent) { this.categoryParent = categoryParent; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCategorySlug() { return categorySlug; }
    public void setCategorySlug(String categorySlug) { this.categorySlug = categorySlug; }
    public String getCategoryDescription() { return categoryDescription; }
    public void setCategoryDescription(String categoryDescription) { this.categoryDescription = categoryDescription; }
    public String getCategoryStatus() { return categoryStatus; }
    public void setCategoryStatus(String categoryStatus) { this.categoryStatus = categoryStatus; }
    public OffsetDateTime getCategoryCreatedAt() { return categoryCreatedAt; }
}
