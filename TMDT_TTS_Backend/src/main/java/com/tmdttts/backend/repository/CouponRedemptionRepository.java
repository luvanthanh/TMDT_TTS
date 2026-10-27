package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.CouponRedemption;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRedemptionRepository extends JpaRepository<CouponRedemption, Long> {
    Optional<CouponRedemption> findByCouponRedemptionOrder_OrderId(Long orderId);

    boolean existsByCouponRedemptionUser_UserIdAndCouponRedemptionCoupon_CouponId(
            Long userId, Long couponId);
}
