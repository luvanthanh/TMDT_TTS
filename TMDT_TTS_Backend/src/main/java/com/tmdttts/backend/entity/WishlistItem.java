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
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;

@Entity
@Table(name = "wishlist_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"wishlist_item_wishlist_id", "wishlist_item_product_id"})
})
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Long wishlistItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_item_wishlist_id", nullable = false)
    private Wishlist wishlistItemWishlist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_item_product_id", nullable = false)
    private Product wishlistItemProduct;

    @Column(name = "wishlist_item_created_at", nullable = false)
    private OffsetDateTime wishlistItemCreatedAt;

    @PrePersist
    void prePersist() {
        if (wishlistItemCreatedAt == null) {
            wishlistItemCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getWishlistItemId() { return wishlistItemId; }
    public Wishlist getWishlistItemWishlist() { return wishlistItemWishlist; }
    public void setWishlistItemWishlist(Wishlist wishlistItemWishlist) { this.wishlistItemWishlist = wishlistItemWishlist; }
    public Product getWishlistItemProduct() { return wishlistItemProduct; }
    public void setWishlistItemProduct(Product wishlistItemProduct) { this.wishlistItemProduct = wishlistItemProduct; }
    public OffsetDateTime getWishlistItemCreatedAt() { return wishlistItemCreatedAt; }
}
