package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentTransactionRef(String transactionRef);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Payment p where p.paymentTransactionRef = :ref")
    Optional<Payment> findByTransactionRefForUpdate(@Param("ref") String transactionRef);

    Optional<Payment> findByPaymentProviderTransactionNo(String providerTransactionNo);

    List<Payment> findByPaymentOrder_OrderIdOrderByPaymentCreatedAtDesc(Long orderId);
}
