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
import java.time.OffsetDateTime;

@Entity
@Table(name = "returns")
public class ProductReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long returnId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_order_id", nullable = false)
    private Order returnOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_user_id", nullable = false)
    private User returnUser;

    @Column(name = "return_reason", nullable = false, columnDefinition = "text")
    private String returnReason;

    @Column(name = "return_status", nullable = false, length = 30)
    private String returnStatus = "REQUESTED";

    @Column(name = "return_admin_note", columnDefinition = "text")
    private String returnAdminNote;

    @Column(name = "return_requested_at", nullable = false)
    private OffsetDateTime returnRequestedAt;

    @Column(name = "return_updated_at", nullable = false)
    private OffsetDateTime returnUpdatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (returnRequestedAt == null) {
            returnRequestedAt = now;
        }
        if (returnUpdatedAt == null) {
            returnUpdatedAt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        returnUpdatedAt = OffsetDateTime.now();
    }

    public Long getReturnId() { return returnId; }
    public Order getReturnOrder() { return returnOrder; }
    public void setReturnOrder(Order returnOrder) { this.returnOrder = returnOrder; }
    public User getReturnUser() { return returnUser; }
    public void setReturnUser(User returnUser) { this.returnUser = returnUser; }
    public String getReturnReason() { return returnReason; }
    public void setReturnReason(String returnReason) { this.returnReason = returnReason; }
    public String getReturnStatus() { return returnStatus; }
    public void setReturnStatus(String returnStatus) { this.returnStatus = returnStatus; }
    public String getReturnAdminNote() { return returnAdminNote; }
    public void setReturnAdminNote(String returnAdminNote) { this.returnAdminNote = returnAdminNote; }
    public OffsetDateTime getReturnRequestedAt() { return returnRequestedAt; }
    public OffsetDateTime getReturnUpdatedAt() { return returnUpdatedAt; }
}
