package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ReturnItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {
    List<ReturnItem> findByReturnItemReturn_ReturnId(Long returnId);

    boolean existsByReturnItemOrderItem_OrderItemId(Long orderItemId);
}
