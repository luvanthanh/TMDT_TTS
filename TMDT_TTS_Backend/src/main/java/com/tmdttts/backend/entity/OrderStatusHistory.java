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
@Table(name = "order_status_history")
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_history_id")
    private Long orderStatusHistoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_status_history_order_id", nullable = false)
    private Order orderStatusHistoryOrder;

    @Column(name = "order_status_history_old_status", length = 30)
    private String orderStatusHistoryOldStatus;

    @Column(name = "order_status_history_new_status", nullable = false, length = 30)
    private String orderStatusHistoryNewStatus;

    @Column(name = "order_status_history_note", columnDefinition = "text")
    private String orderStatusHistoryNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_history_changed_by_user_id")
    private User orderStatusHistoryChangedByUser;

    @Column(name = "order_status_history_changed_at", nullable = false)
    private OffsetDateTime orderStatusHistoryChangedAt;

    @PrePersist
    void prePersist() {
        if (orderStatusHistoryChangedAt == null) {
            orderStatusHistoryChangedAt = OffsetDateTime.now();
        }
    }

    public Long getOrderStatusHistoryId() { return orderStatusHistoryId; }
    public Order getOrderStatusHistoryOrder() { return orderStatusHistoryOrder; }
    public void setOrderStatusHistoryOrder(Order orderStatusHistoryOrder) { this.orderStatusHistoryOrder = orderStatusHistoryOrder; }
    public String getOrderStatusHistoryOldStatus() { return orderStatusHistoryOldStatus; }
    public void setOrderStatusHistoryOldStatus(String orderStatusHistoryOldStatus) { this.orderStatusHistoryOldStatus = orderStatusHistoryOldStatus; }
    public String getOrderStatusHistoryNewStatus() { return orderStatusHistoryNewStatus; }
    public void setOrderStatusHistoryNewStatus(String orderStatusHistoryNewStatus) { this.orderStatusHistoryNewStatus = orderStatusHistoryNewStatus; }
    public String getOrderStatusHistoryNote() { return orderStatusHistoryNote; }
    public void setOrderStatusHistoryNote(String orderStatusHistoryNote) { this.orderStatusHistoryNote = orderStatusHistoryNote; }
    public User getOrderStatusHistoryChangedByUser() { return orderStatusHistoryChangedByUser; }
    public void setOrderStatusHistoryChangedByUser(User orderStatusHistoryChangedByUser) { this.orderStatusHistoryChangedByUser = orderStatusHistoryChangedByUser; }
    public OffsetDateTime getOrderStatusHistoryChangedAt() { return orderStatusHistoryChangedAt; }
}
