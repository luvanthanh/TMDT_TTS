package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.*;
import com.tmdttts.backend.mapper.ApiMapper;
import com.tmdttts.backend.repository.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerListService {
    private final UserRepository users; private final CartRepository carts; private final CartItemRepository cartItems;
    private final ProductVariantRepository variants; private final ProductRepository products;
    private final WishlistRepository wishlists; private final WishlistItemRepository wishlistItems; private final ApiMapper mapper;
    public CustomerListService(UserRepository users,CartRepository carts,CartItemRepository cartItems,
            ProductVariantRepository variants,ProductRepository products,WishlistRepository wishlists,
            WishlistItemRepository wishlistItems,ApiMapper mapper) {
        this.users=users;this.carts=carts;this.cartItems=cartItems;this.variants=variants;this.products=products;
        this.wishlists=wishlists;this.wishlistItems=wishlistItems;this.mapper=mapper;
    }
    @Transactional(readOnly=true) public Responses.Cart cart(Long userId) {
        var lines=carts.findByCartUser_UserId(userId).map(c->cartItems.findByCartItemCart_CartIdOrderByCartItemAddedAtAsc(c.getCartId())).orElse(List.of());
        var result=lines.stream().map(mapper::cartLine).toList();
        var subtotal=result.stream().map(Responses.CartLine::lineTotal).reduce(java.math.BigDecimal.ZERO,java.math.BigDecimal::add);
        return new Responses.Cart(result,subtotal,result.stream().mapToInt(Responses.CartLine::quantity).sum());
    }
    @Transactional public Responses.Cart addCartItem(Long userId,Long variantId,int quantity) {
        if(quantity<1||quantity>99) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Số lượng phải từ 1 đến 99");
        User user=user(userId); Cart cart=carts.findByCartUser_UserId(userId).orElseGet(()->{Cart c=new Cart();c.setCartUser(user);return carts.save(c);});
        ProductVariant variant=variants.findByIdForUpdate(variantId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phân loại sản phẩm"));
        if(!"ACTIVE".equals(variant.getProductVariantStatus())||!"ACTIVE".equals(variant.getProductVariantProduct().getProductStatus())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sản phẩm hiện không bán");
        CartItem item=cartItems.findByCartItemCart_CartIdAndCartItemProductVariant_ProductVariantId(cart.getCartId(),variantId).orElseGet(()->{CartItem i=new CartItem();i.setCartItemCart(cart);i.setCartItemProductVariant(variant);i.setCartItemQuantity(0);return i;});
        int newQuantity=item.getCartItemQuantity()+quantity;
        if(newQuantity>variant.getProductVariantStockQuantity()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Số lượng vượt quá tồn kho");
        item.setCartItemQuantity(newQuantity);cartItems.save(item);return cart(userId);
    }
    @Transactional public Responses.Cart updateCartItem(Long userId,Long itemId,int quantity) {
        if(quantity<1||quantity>99) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Số lượng phải từ 1 đến 99");
        Cart cart=carts.findByCartUser_UserId(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Giỏ hàng trống"));
        CartItem item=cartItems.findByCartItemIdAndCartItemCart_CartId(itemId,cart.getCartId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm trong giỏ"));
        if(quantity>item.getCartItemProductVariant().getProductVariantStockQuantity()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Số lượng vượt quá tồn kho");
        item.setCartItemQuantity(quantity);return cart(userId);
    }
    @Transactional public Responses.Cart removeCartItem(Long userId,Long itemId) {
        Cart cart=carts.findByCartUser_UserId(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Giỏ hàng trống"));
        CartItem item=cartItems.findByCartItemIdAndCartItemCart_CartId(itemId,cart.getCartId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm trong giỏ"));
        cartItems.delete(item);return cart(userId);
    }
    @Transactional(readOnly=true) public List<Responses.Product> wishlist(Long userId) {
        return wishlists.findByWishlistUser_UserId(userId).map(w->wishlistItems.findByWishlistItemWishlist_WishlistIdOrderByWishlistItemCreatedAtDesc(w.getWishlistId()).stream().map(i->mapper.product(i.getWishlistItemProduct())).toList()).orElse(List.of());
    }
    @Transactional public List<Responses.Product> addWishlist(Long userId,Long productId) {
        User user=user(userId); Product product=products.findById(productId).filter(p->"ACTIVE".equals(p.getProductStatus())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));
        Wishlist wishlist=wishlists.findByWishlistUser_UserId(userId).orElseGet(()->{Wishlist w=new Wishlist();w.setWishlistUser(user);return wishlists.save(w);});
        if(wishlistItems.findByWishlistItemWishlist_WishlistIdAndWishlistItemProduct_ProductId(wishlist.getWishlistId(),productId).isEmpty()) {
            WishlistItem item=new WishlistItem();item.setWishlistItemWishlist(wishlist);item.setWishlistItemProduct(product);wishlistItems.save(item);
        }
        return wishlist(userId);
    }
    @Transactional public List<Responses.Product> removeWishlist(Long userId,Long productId) {
        Wishlist wishlist=wishlists.findByWishlistUser_UserId(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wishlist trống"));
        WishlistItem item=wishlistItems.findByWishlistItemWishlist_WishlistIdAndWishlistItemProduct_ProductId(wishlist.getWishlistId(),productId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm trong wishlist"));
        wishlistItems.delete(item);return wishlist(userId);
    }
    private User user(Long id){return users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Tài khoản không tồn tại"));}
}
