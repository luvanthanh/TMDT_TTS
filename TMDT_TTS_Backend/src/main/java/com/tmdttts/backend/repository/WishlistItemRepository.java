package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.WishlistItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlistItemWishlist_WishlistIdOrderByWishlistItemCreatedAtDesc(
            Long wishlistId);

    Optional<WishlistItem> findByWishlistItemWishlist_WishlistIdAndWishlistItemProduct_ProductId(
            Long wishlistId, Long productId);

    Optional<WishlistItem> findByWishlistItemIdAndWishlistItemWishlist_WishlistId(
            Long wishlistItemId, Long wishlistId);

    void deleteByWishlistItemWishlist_WishlistId(Long wishlistId);
}
