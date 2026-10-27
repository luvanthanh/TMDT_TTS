package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ProductVariant;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findByProductVariantSku(String sku);

    List<ProductVariant> findByProductVariantProduct_ProductIdAndProductVariantStatus(
            Long productId, String status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select variant from ProductVariant variant where variant.productVariantId = :variantId")
    Optional<ProductVariant> findByIdForUpdate(@Param("variantId") Long variantId);
}
