package com.tmdttts.backend.repository;

import com.tmdttts.backend.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCartUser_UserId(Long userId);
}
