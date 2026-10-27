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
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_order_id", nullable = false)
    private Order paymentOrder;

    @Column(name = "payment_provider", nullable = false, length = 30)
    private String paymentProvider = "VNPAY";

    @Column(name = "payment_transaction_ref", nullable = false, unique = true, length = 100)
    private String paymentTransactionRef;

    @Column(name = "payment_provider_transaction_no", length = 100)
    private String paymentProviderTransactionNo;

    @Column(name = "payment_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "payment_currency", nullable = false, length = 3)
    private String paymentCurrency = "VND";

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus = "PENDING";

    @Column(name = "payment_response_code", length = 20)
    private String paymentResponseCode;

    @Column(name = "payment_bank_code", length = 50)
    private String paymentBankCode;

    @Column(name = "payment_card_type", length = 30)
    private String paymentCardType;

    @Column(name = "payment_created_at", nullable = false)
    private OffsetDateTime paymentCreatedAt;

    @Column(name = "payment_updated_at", nullable = false)
    private OffsetDateTime paymentUpdatedAt;

    @Column(name = "payment_paid_at")
    private OffsetDateTime paymentPaidAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (paymentCreatedAt == null) {
            paymentCreatedAt = now;
        }
        if (paymentUpdatedAt == null) {
            paymentUpdatedAt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        paymentUpdatedAt = OffsetDateTime.now();
    }

    public Long getPaymentId() { return paymentId; }
    public Order getPaymentOrder() { return paymentOrder; }
    public void setPaymentOrder(Order paymentOrder) { this.paymentOrder = paymentOrder; }
    public String getPaymentProvider() { return paymentProvider; }
    public void setPaymentProvider(String paymentProvider) { this.paymentProvider = paymentProvider; }
    public String getPaymentTransactionRef() { return paymentTransactionRef; }
    public void setPaymentTransactionRef(String paymentTransactionRef) { this.paymentTransactionRef = paymentTransactionRef; }
    public String getPaymentProviderTransactionNo() { return paymentProviderTransactionNo; }
    public void setPaymentProviderTransactionNo(String paymentProviderTransactionNo) { this.paymentProviderTransactionNo = paymentProviderTransactionNo; }
    public BigDecimal getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(BigDecimal paymentAmount) { this.paymentAmount = paymentAmount; }
    public String getPaymentCurrency() { return paymentCurrency; }
    public void setPaymentCurrency(String paymentCurrency) { this.paymentCurrency = paymentCurrency; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentResponseCode() { return paymentResponseCode; }
    public void setPaymentResponseCode(String paymentResponseCode) { this.paymentResponseCode = paymentResponseCode; }
    public String getPaymentBankCode() { return paymentBankCode; }
    public void setPaymentBankCode(String paymentBankCode) { this.paymentBankCode = paymentBankCode; }
    public String getPaymentCardType() { return paymentCardType; }
    public void setPaymentCardType(String paymentCardType) { this.paymentCardType = paymentCardType; }
    public OffsetDateTime getPaymentCreatedAt() { return paymentCreatedAt; }
    public OffsetDateTime getPaymentUpdatedAt() { return paymentUpdatedAt; }
    public OffsetDateTime getPaymentPaidAt() { return paymentPaidAt; }
    public void setPaymentPaidAt(OffsetDateTime paymentPaidAt) { this.paymentPaidAt = paymentPaidAt; }
}
