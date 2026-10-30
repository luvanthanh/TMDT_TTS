package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.response.order.OrderItemResponse;
import com.tmdttts.backend.dto.response.order.OrderResponse;

import com.tmdttts.backend.entity.Order;
import com.tmdttts.backend.entity.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order,List<OrderItem> lines){
        var items=lines.stream().map(i->new OrderItemResponse(i.getOrderItemId(),i.getOrderItemProductName(),i.getOrderItemSkuSnapshot(),i.getOrderItemVariantSnapshot(),i.getOrderItemUnitPrice(),i.getOrderItemQuantity(),i.getOrderItemLineTotal())).toList();
        return new OrderResponse(order.getOrderId(),order.getOrderCode(),order.getOrderStatus(),order.getOrderPaymentMethod(),order.getOrderPaymentStatus(),
                order.getOrderSubtotal(),order.getOrderDiscountAmount(),order.getOrderShippingFee(),order.getOrderTotalAmount(),order.getOrderRecipientName(),
                order.getOrderRecipientPhone(),order.getOrderShippingAddress(),order.getOrderCreatedAt(),items);
    }
}
