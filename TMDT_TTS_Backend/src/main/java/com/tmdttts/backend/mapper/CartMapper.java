package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.response.cart.CartItemResponse;
import com.tmdttts.backend.dto.response.cart.CartResponse;

import com.tmdttts.backend.entity.*;
import com.tmdttts.backend.repository.ProductImageRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    private final ProductImageRepository images;
    public CartMapper(ProductImageRepository images){this.images=images;}
    public CartItemResponse toItemResponse(CartItem item){
        ProductVariant variant=item.getCartItemProductVariant();Product product=variant.getProductVariantProduct();
        BigDecimal price=variant.getProductVariantPriceOverride()!=null?variant.getProductVariantPriceOverride():
                (product.getProductSalePrice()!=null?product.getProductSalePrice():product.getProductPrice());
        String image=images.findByProductImageProduct_ProductIdAndProductImageIsPrimaryTrue(product.getProductId()).map(ProductImage::getProductImageUrl).orElse(null);
        return new CartItemResponse(item.getCartItemId(),variant.getProductVariantId(),product.getProductId(),product.getProductName(),image,
                variant.getProductVariantSku(),variant.getProductVariantColor(),variant.getProductVariantSize(),price,
                item.getCartItemQuantity(),variant.getProductVariantStockQuantity(),price.multiply(BigDecimal.valueOf(item.getCartItemQuantity())));
    }
    public CartResponse toResponse(List<CartItemResponse> items){
        BigDecimal subtotal=items.stream().map(CartItemResponse::lineTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new CartResponse(items,subtotal,items.stream().mapToInt(CartItemResponse::quantity).sum());
    }
}
