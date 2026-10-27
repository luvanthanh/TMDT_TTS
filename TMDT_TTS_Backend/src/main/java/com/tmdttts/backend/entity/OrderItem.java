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
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_order_id", nullable = false)
    private Order orderItemOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_product_variant_id")
    private ProductVariant orderItemProductVariant;

    @Column(name = "order_item_product_name", nullable = false, length = 255)
    private String orderItemProductName;

    @Column(name = "order_item_sku_snapshot", length = 100)
    private String orderItemSkuSnapshot;

    @Column(name = "order_item_variant_snapshot", length = 180)
    private String orderItemVariantSnapshot;

    @Column(name = "order_item_unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderItemUnitPrice;

    @Column(name = "order_item_quantity", nullable = false)
    private int orderItemQuantity;

    @Column(name = "order_item_line_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderItemLineTotal;

    public Long getOrderItemId() { return orderItemId; }
    public Order getOrderItemOrder() { return orderItemOrder; }
    public void setOrderItemOrder(Order orderItemOrder) { this.orderItemOrder = orderItemOrder; }
    public ProductVariant getOrderItemProductVariant() { return orderItemProductVariant; }
    public void setOrderItemProductVariant(ProductVariant orderItemProductVariant) { this.orderItemProductVariant = orderItemProductVariant; }
    public String getOrderItemProductName() { return orderItemProductName; }
    public void setOrderItemProductName(String orderItemProductName) { this.orderItemProductName = orderItemProductName; }
    public String getOrderItemSkuSnapshot() { return orderItemSkuSnapshot; }
    public void setOrderItemSkuSnapshot(String orderItemSkuSnapshot) { this.orderItemSkuSnapshot = orderItemSkuSnapshot; }
    public String getOrderItemVariantSnapshot() { return orderItemVariantSnapshot; }
    public void setOrderItemVariantSnapshot(String orderItemVariantSnapshot) { this.orderItemVariantSnapshot = orderItemVariantSnapshot; }
    public BigDecimal getOrderItemUnitPrice() { return orderItemUnitPrice; }
    public void setOrderItemUnitPrice(BigDecimal orderItemUnitPrice) { this.orderItemUnitPrice = orderItemUnitPrice; }
    public int getOrderItemQuantity() { return orderItemQuantity; }
    public void setOrderItemQuantity(int orderItemQuantity) { this.orderItemQuantity = orderItemQuantity; }
    public BigDecimal getOrderItemLineTotal() { return orderItemLineTotal; }
    public void setOrderItemLineTotal(BigDecimal orderItemLineTotal) { this.orderItemLineTotal = orderItemLineTotal; }
}
