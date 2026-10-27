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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_user_id", nullable = false, unique = true)
    private User cartUser;

    @Column(name = "cart_created_at", nullable = false)
    private OffsetDateTime cartCreatedAt;

    @Column(name = "cart_updated_at", nullable = false)
    private OffsetDateTime cartUpdatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (cartCreatedAt == null) {
            cartCreatedAt = now;
        }
        if (cartUpdatedAt == null) {
            cartUpdatedAt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        cartUpdatedAt = OffsetDateTime.now();
    }

    public Long getCartId() { return cartId; }
    public User getCartUser() { return cartUser; }
    public void setCartUser(User cartUser) { this.cartUser = cartUser; }
    public OffsetDateTime getCartCreatedAt() { return cartCreatedAt; }
    public OffsetDateTime getCartUpdatedAt() { return cartUpdatedAt; }
}
