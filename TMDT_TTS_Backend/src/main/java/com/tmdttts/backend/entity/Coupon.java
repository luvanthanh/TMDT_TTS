package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_code", nullable = false, unique = true, length = 50)
    private String couponCode;

    @Column(name = "coupon_description", columnDefinition = "text")
    private String couponDescription;

    @Column(name = "coupon_discount_type", nullable = false, length = 20)
    private String couponDiscountType;

    @Column(name = "coupon_discount_value", nullable = false, precision = 12, scale = 2)
    private BigDecimal couponDiscountValue;

    @Column(name = "coupon_min_order_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal couponMinOrderAmount = BigDecimal.ZERO;

    @Column(name = "coupon_max_discount_amount", precision = 12, scale = 2)
    private BigDecimal couponMaxDiscountAmount;

    @Column(name = "coupon_start_at")
    private OffsetDateTime couponStartAt;

    @Column(name = "coupon_end_at")
    private OffsetDateTime couponEndAt;

    @Column(name = "coupon_usage_limit")
    private Integer couponUsageLimit;

    @Column(name = "coupon_used_count", nullable = false)
    private int couponUsedCount;

    @Column(name = "coupon_status", nullable = false, length = 20)
    private String couponStatus = "ACTIVE";

    public Long getCouponId() { return couponId; }
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
    public String getCouponDescription() { return couponDescription; }
    public void setCouponDescription(String couponDescription) { this.couponDescription = couponDescription; }
    public String getCouponDiscountType() { return couponDiscountType; }
    public void setCouponDiscountType(String couponDiscountType) { this.couponDiscountType = couponDiscountType; }
    public BigDecimal getCouponDiscountValue() { return couponDiscountValue; }
    public void setCouponDiscountValue(BigDecimal couponDiscountValue) { this.couponDiscountValue = couponDiscountValue; }
    public BigDecimal getCouponMinOrderAmount() { return couponMinOrderAmount; }
    public void setCouponMinOrderAmount(BigDecimal couponMinOrderAmount) { this.couponMinOrderAmount = couponMinOrderAmount; }
    public BigDecimal getCouponMaxDiscountAmount() { return couponMaxDiscountAmount; }
    public void setCouponMaxDiscountAmount(BigDecimal couponMaxDiscountAmount) { this.couponMaxDiscountAmount = couponMaxDiscountAmount; }
    public OffsetDateTime getCouponStartAt() { return couponStartAt; }
    public void setCouponStartAt(OffsetDateTime couponStartAt) { this.couponStartAt = couponStartAt; }
    public OffsetDateTime getCouponEndAt() { return couponEndAt; }
    public void setCouponEndAt(OffsetDateTime couponEndAt) { this.couponEndAt = couponEndAt; }
    public Integer getCouponUsageLimit() { return couponUsageLimit; }
    public void setCouponUsageLimit(Integer couponUsageLimit) { this.couponUsageLimit = couponUsageLimit; }
    public int getCouponUsedCount() { return couponUsedCount; }
    public void setCouponUsedCount(int couponUsedCount) { this.couponUsedCount = couponUsedCount; }
    public String getCouponStatus() { return couponStatus; }
    public void setCouponStatus(String couponStatus) { this.couponStatus = couponStatus; }
}
