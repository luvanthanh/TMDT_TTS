package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentTransactionRef(String transactionRef);

    Optional<Payment> findByPaymentProviderTransactionNo(String providerTransactionNo);

    List<Payment> findByPaymentOrder_OrderIdOrderByPaymentCreatedAtDesc(Long orderId);
}
