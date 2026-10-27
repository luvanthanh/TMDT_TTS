package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartItemCart_CartIdOrderByCartItemAddedAtAsc(Long cartId);

    Optional<CartItem> findByCartItemCart_CartIdAndCartItemProductVariant_ProductVariantId(
            Long cartId, Long variantId);

    Optional<CartItem> findByCartItemIdAndCartItemCart_CartId(Long cartItemId, Long cartId);

    void deleteByCartItemCart_CartId(Long cartId);
}
