package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Wishlist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByWishlistUser_UserId(Long userId);
}
