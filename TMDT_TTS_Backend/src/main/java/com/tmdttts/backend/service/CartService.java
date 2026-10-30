package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.response.cart.CartResponse;
import com.tmdttts.backend.entity.Cart;
import com.tmdttts.backend.entity.CartItem;
import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.mapper.CartMapper;
import com.tmdttts.backend.repository.CartItemRepository;
import com.tmdttts.backend.repository.CartRepository;
import com.tmdttts.backend.repository.ProductVariantRepository;
import com.tmdttts.backend.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartService {
    private final UserRepository users;
    private final CartRepository carts;
    private final CartItemRepository cartItems;
    private final ProductVariantRepository variants;
    private final CartMapper mapper;

    public CartService(UserRepository users, CartRepository carts, CartItemRepository cartItems,
                       ProductVariantRepository variants, CartMapper mapper) {
        this.users = users;
        this.carts = carts;
        this.cartItems = cartItems;
        this.variants = variants;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        var items = carts.findByCartUser_UserId(userId)
                .map(cart -> cartItems.findByCartItemCart_CartIdOrderByCartItemAddedAtAsc(cart.getCartId()))
                .orElse(List.of());
        return mapper.toResponse(items.stream().map(mapper::toItemResponse).toList());
    }

    @Transactional
    public CartResponse addItem(Long userId, Long variantId, int quantity) {
        validateQuantity(quantity);
        User user = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not found"));
        Cart cart = carts.findByCartUser_UserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCartUser(user);
            return carts.save(newCart);
        });
        var variant = variants.findByIdForUpdate(variantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product variant not found"));
        if (!"ACTIVE".equals(variant.getProductVariantStatus())
                || !"ACTIVE".equals(variant.getProductVariantProduct().getProductStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is not available");
        }
        CartItem item = cartItems.findByCartItemCart_CartIdAndCartItemProductVariant_ProductVariantId(cart.getCartId(), variantId)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCartItemCart(cart);
                    newItem.setCartItemProductVariant(variant);
                    newItem.setCartItemQuantity(0);
                    return newItem;
                });
        int updatedQuantity = item.getCartItemQuantity() + quantity;
        if (updatedQuantity > variant.getProductVariantStockQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Requested quantity exceeds stock");
        }
        item.setCartItemQuantity(updatedQuantity);
        cartItems.save(item);
        return getCart(userId);
    }

    @Transactional
    public CartResponse updateItem(Long userId, Long itemId, int quantity) {
        validateQuantity(quantity);
        Cart cart = carts.findByCartUser_UserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        CartItem item = cartItems.findByCartItemIdAndCartItemCart_CartId(itemId, cart.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));
        if (quantity > item.getCartItemProductVariant().getProductVariantStockQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Requested quantity exceeds stock");
        }
        item.setCartItemQuantity(quantity);
        return getCart(userId);
    }

    @Transactional
    public CartResponse removeItem(Long userId, Long itemId) {
        Cart cart = carts.findByCartUser_UserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        CartItem item = cartItems.findByCartItemIdAndCartItemCart_CartId(itemId, cart.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));
        cartItems.delete(item);
        return getCart(userId);
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1 || quantity > 99) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be between 1 and 99");
        }
    }
}
