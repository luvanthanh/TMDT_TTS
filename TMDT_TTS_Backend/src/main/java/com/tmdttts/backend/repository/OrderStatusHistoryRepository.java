package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.OrderStatusHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrderStatusHistoryOrder_OrderIdOrderByOrderStatusHistoryChangedAtAsc(
            Long orderId);
}
