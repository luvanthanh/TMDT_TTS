package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "wishlists")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_user_id", nullable = false, unique = true)
    private User wishlistUser;

    @Column(name = "wishlist_created_at", nullable = false)
    private OffsetDateTime wishlistCreatedAt;

    @PrePersist
    void prePersist() {
        if (wishlistCreatedAt == null) {
            wishlistCreatedAt = OffsetDateTime.now();
        }
    }

    public Long getWishlistId() { return wishlistId; }
    public User getWishlistUser() { return wishlistUser; }
    public void setWishlistUser(User wishlistUser) { this.wishlistUser = wishlistUser; }
    public OffsetDateTime getWishlistCreatedAt() { return wishlistCreatedAt; }
}
