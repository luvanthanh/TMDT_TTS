package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Coupon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponCodeIgnoreCase(String couponCode);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Coupon c where lower(c.couponCode) = lower(:code)")
    Optional<Coupon> findByCodeForUpdate(@Param("code") String code);
}
