package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderItemOrder_OrderId(Long orderId);

    List<OrderItem> findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(Long orderId);
}
