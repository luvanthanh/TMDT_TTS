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
@Table(name = "coupon_redemptions")
public class CouponRedemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_redemption_id")
    private Long couponRedemptionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_redemption_coupon_id", nullable = false)
    private Coupon couponRedemptionCoupon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_redemption_user_id", nullable = false)
    private User couponRedemptionUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_redemption_order_id", nullable = false, unique = true)
    private Order couponRedemptionOrder;

    @Column(name = "coupon_redemption_redeemed_at", nullable = false)
    private OffsetDateTime couponRedemptionRedeemedAt;

    @PrePersist
    void prePersist() {
        if (couponRedemptionRedeemedAt == null) {
            couponRedemptionRedeemedAt = OffsetDateTime.now();
        }
    }

    public Long getCouponRedemptionId() { return couponRedemptionId; }
    public Coupon getCouponRedemptionCoupon() { return couponRedemptionCoupon; }
    public void setCouponRedemptionCoupon(Coupon couponRedemptionCoupon) { this.couponRedemptionCoupon = couponRedemptionCoupon; }
    public User getCouponRedemptionUser() { return couponRedemptionUser; }
    public void setCouponRedemptionUser(User couponRedemptionUser) { this.couponRedemptionUser = couponRedemptionUser; }
    public Order getCouponRedemptionOrder() { return couponRedemptionOrder; }
    public void setCouponRedemptionOrder(Order couponRedemptionOrder) { this.couponRedemptionOrder = couponRedemptionOrder; }
    public OffsetDateTime getCouponRedemptionRedeemedAt() { return couponRedemptionRedeemedAt; }
}
