package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.response.product.ProductResponse;
import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.entity.Wishlist;
import com.tmdttts.backend.entity.WishlistItem;
import com.tmdttts.backend.mapper.ProductMapper;
import com.tmdttts.backend.repository.ProductRepository;
import com.tmdttts.backend.repository.UserRepository;
import com.tmdttts.backend.repository.WishlistItemRepository;
import com.tmdttts.backend.repository.WishlistRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishlistService {
    private final UserRepository users;
    private final ProductRepository products;
    private final WishlistRepository wishlists;
    private final WishlistItemRepository items;
    private final ProductMapper mapper;

    public WishlistService(UserRepository users, ProductRepository products, WishlistRepository wishlists,
                           WishlistItemRepository items, ProductMapper mapper) {
        this.users = users;
        this.products = products;
        this.wishlists = wishlists;
        this.items = items;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getWishlist(Long userId) {
        return wishlists.findByWishlistUser_UserId(userId)
                .map(wishlist -> items.findByWishlistItemWishlist_WishlistIdOrderByWishlistItemCreatedAtDesc(wishlist.getWishlistId())
                        .stream().map(item -> mapper.toResponse(item.getWishlistItemProduct())).toList())
                .orElse(List.of());
    }

    @Transactional
    public List<ProductResponse> addProduct(Long userId, Long productId) {
        User user = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not found"));
        var product = products.findById(productId).filter(p -> "ACTIVE".equals(p.getProductStatus()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Wishlist wishlist = wishlists.findByWishlistUser_UserId(userId).orElseGet(() -> {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setWishlistUser(user);
            return wishlists.save(newWishlist);
        });
        if (items.findByWishlistItemWishlist_WishlistIdAndWishlistItemProduct_ProductId(wishlist.getWishlistId(), productId).isEmpty()) {
            WishlistItem item = new WishlistItem();
            item.setWishlistItemWishlist(wishlist);
            item.setWishlistItemProduct(product);
            items.save(item);
        }
        return getWishlist(userId);
    }

    @Transactional
    public List<ProductResponse> removeProduct(Long userId, Long productId) {
        Wishlist wishlist = wishlists.findByWishlistUser_UserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist not found"));
        WishlistItem item = items.findByWishlistItemWishlist_WishlistIdAndWishlistItemProduct_ProductId(wishlist.getWishlistId(), productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found"));
        items.delete(item);
        return getWishlist(userId);
    }
}
