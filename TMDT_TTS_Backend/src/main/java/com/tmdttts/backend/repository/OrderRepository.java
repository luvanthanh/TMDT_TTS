package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

    Optional<Order> findByOrderIdAndOrderUser_UserId(Long orderId, Long userId);

    List<Order> findByOrderUser_UserIdOrderByOrderCreatedAtDesc(Long userId);

    List<Order> findByOrderUser_UserIdAndOrderStatusOrderByOrderCreatedAtDesc(
            Long userId, String status);
}
