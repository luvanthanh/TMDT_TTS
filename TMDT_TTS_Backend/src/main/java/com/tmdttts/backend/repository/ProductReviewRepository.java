package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ProductReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductReviewProduct_ProductIdAndProductReviewStatusOrderByProductReviewCreatedAtDesc(
            Long productId, String status);

    List<ProductReview> findByProductReviewUser_UserIdOrderByProductReviewCreatedAtDesc(
            Long userId);

    boolean existsByProductReviewOrderItem_OrderItemId(Long orderItemId);
}
