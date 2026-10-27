package com.tmdttts.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "return_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"return_item_return_id", "return_item_order_item_id"})
})
public class ReturnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_item_id")
    private Long returnItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_item_return_id", nullable = false)
    private ProductReturn returnItemReturn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_item_order_item_id", nullable = false)
    private OrderItem returnItemOrderItem;

    @Column(name = "return_item_quantity", nullable = false)
    private int returnItemQuantity;

    public Long getReturnItemId() { return returnItemId; }
    public ProductReturn getReturnItemReturn() { return returnItemReturn; }
    public void setReturnItemReturn(ProductReturn returnItemReturn) { this.returnItemReturn = returnItemReturn; }
    public OrderItem getReturnItemOrderItem() { return returnItemOrderItem; }
    public void setReturnItemOrderItem(OrderItem returnItemOrderItem) { this.returnItemOrderItem = returnItemOrderItem; }
    public int getReturnItemQuantity() { return returnItemQuantity; }
    public void setReturnItemQuantity(int returnItemQuantity) { this.returnItemQuantity = returnItemQuantity; }
}
