package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Coupon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponCodeIgnoreCase(String couponCode);
}
