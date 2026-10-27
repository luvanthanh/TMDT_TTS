package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ProductReturn;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {
    List<ProductReturn> findByReturnUser_UserIdOrderByReturnRequestedAtDesc(Long userId);

    List<ProductReturn> findByReturnUser_UserIdAndReturnStatusOrderByReturnRequestedAtDesc(
            Long userId, String status);

    Optional<ProductReturn> findByReturnIdAndReturnUser_UserId(Long returnId, Long userId);
}
