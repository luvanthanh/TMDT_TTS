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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code", nullable = false, unique = true, length = 40)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_user_id", nullable = false)
    private User orderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_coupon_id")
    private Coupon orderCoupon;

    @Column(name = "order_coupon_code_snapshot", length = 50)
    private String orderCouponCodeSnapshot;

    @Column(name = "order_recipient_name", nullable = false, length = 150)
    private String orderRecipientName;

    @Column(name = "order_recipient_phone", nullable = false, length = 30)
    private String orderRecipientPhone;

    @Column(name = "order_shipping_address", nullable = false, columnDefinition = "text")
    private String orderShippingAddress;

    @Column(name = "order_subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderSubtotal;

    @Column(name = "order_discount_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderDiscountAmount = BigDecimal.ZERO;

    @Column(name = "order_shipping_fee", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderShippingFee = BigDecimal.ZERO;

    @Column(name = "order_total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderTotalAmount;

    @Column(name = "order_payment_method", nullable = false, length = 30)
    private String orderPaymentMethod = "COD";

    @Column(name = "order_payment_status", nullable = false, length = 30)
    private String orderPaymentStatus = "UNPAID";

    @Column(name = "order_status", nullable = false, length = 30)
    private String orderStatus = "PENDING";

    @Column(name = "order_customer_note", columnDefinition = "text")
    private String orderCustomerNote;

    @Column(name = "order_created_at", nullable = false)
    private OffsetDateTime orderCreatedAt;

    @Column(name = "order_updated_at", nullable = false)
    private OffsetDateTime orderUpdatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (orderCreatedAt == null) {
            orderCreatedAt = now;
        }
        if (orderUpdatedAt == null) {
            orderUpdatedAt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        orderUpdatedAt = OffsetDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }
    public User getOrderUser() { return orderUser; }
    public void setOrderUser(User orderUser) { this.orderUser = orderUser; }
    public Coupon getOrderCoupon() { return orderCoupon; }
    public void setOrderCoupon(Coupon orderCoupon) { this.orderCoupon = orderCoupon; }
    public String getOrderCouponCodeSnapshot() { return orderCouponCodeSnapshot; }
    public void setOrderCouponCodeSnapshot(String orderCouponCodeSnapshot) { this.orderCouponCodeSnapshot = orderCouponCodeSnapshot; }
    public String getOrderRecipientName() { return orderRecipientName; }
    public void setOrderRecipientName(String orderRecipientName) { this.orderRecipientName = orderRecipientName; }
    public String getOrderRecipientPhone() { return orderRecipientPhone; }
    public void setOrderRecipientPhone(String orderRecipientPhone) { this.orderRecipientPhone = orderRecipientPhone; }
    public String getOrderShippingAddress() { return orderShippingAddress; }
    public void setOrderShippingAddress(String orderShippingAddress) { this.orderShippingAddress = orderShippingAddress; }
    public BigDecimal getOrderSubtotal() { return orderSubtotal; }
    public void setOrderSubtotal(BigDecimal orderSubtotal) { this.orderSubtotal = orderSubtotal; }
    public BigDecimal getOrderDiscountAmount() { return orderDiscountAmount; }
    public void setOrderDiscountAmount(BigDecimal orderDiscountAmount) { this.orderDiscountAmount = orderDiscountAmount; }
    public BigDecimal getOrderShippingFee() { return orderShippingFee; }
    public void setOrderShippingFee(BigDecimal orderShippingFee) { this.orderShippingFee = orderShippingFee; }
    public BigDecimal getOrderTotalAmount() { return orderTotalAmount; }
    public void setOrderTotalAmount(BigDecimal orderTotalAmount) { this.orderTotalAmount = orderTotalAmount; }
    public String getOrderPaymentMethod() { return orderPaymentMethod; }
    public void setOrderPaymentMethod(String orderPaymentMethod) { this.orderPaymentMethod = orderPaymentMethod; }
    public String getOrderPaymentStatus() { return orderPaymentStatus; }
    public void setOrderPaymentStatus(String orderPaymentStatus) { this.orderPaymentStatus = orderPaymentStatus; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public String getOrderCustomerNote() { return orderCustomerNote; }
    public void setOrderCustomerNote(String orderCustomerNote) { this.orderCustomerNote = orderCustomerNote; }
    public OffsetDateTime getOrderCreatedAt() { return orderCreatedAt; }
    public OffsetDateTime getOrderUpdatedAt() { return orderUpdatedAt; }
}
