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
@Table(name = "cart_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cart_item_cart_id", "cart_item_product_variant_id"})
})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_item_cart_id", nullable = false)
    private Cart cartItemCart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_item_product_variant_id", nullable = false)
    private ProductVariant cartItemProductVariant;

    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;

    @Column(name = "cart_item_added_at", nullable = false)
    private OffsetDateTime cartItemAddedAt;

    @PrePersist
    void prePersist() {
        if (cartItemAddedAt == null) {
            cartItemAddedAt = OffsetDateTime.now();
        }
    }

    public Long getCartItemId() { return cartItemId; }
    public Cart getCartItemCart() { return cartItemCart; }
    public void setCartItemCart(Cart cartItemCart) { this.cartItemCart = cartItemCart; }
    public ProductVariant getCartItemProductVariant() { return cartItemProductVariant; }
    public void setCartItemProductVariant(ProductVariant cartItemProductVariant) { this.cartItemProductVariant = cartItemProductVariant; }
    public int getCartItemQuantity() { return cartItemQuantity; }
    public void setCartItemQuantity(int cartItemQuantity) { this.cartItemQuantity = cartItemQuantity; }
    public OffsetDateTime getCartItemAddedAt() { return cartItemAddedAt; }
}
