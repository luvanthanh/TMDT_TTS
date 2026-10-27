package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.ProductImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductImageProduct_ProductIdOrderByProductImageSortOrderAscProductImageIdAsc(
            Long productId);

    Optional<ProductImage> findByProductImageProduct_ProductIdAndProductImageIsPrimaryTrue(Long productId);

    void deleteByProductImageProduct_ProductId(Long productId);
}
