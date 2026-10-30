package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.*;
import com.tmdttts.backend.repository.ProductImageRepository;
import com.tmdttts.backend.repository.ProductVariantRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ApiMapper {
    private final ProductImageRepository images;
    private final ProductVariantRepository variants;
    public ApiMapper(ProductImageRepository images, ProductVariantRepository variants) {
        this.images = images; this.variants = variants;
    }

    public Responses.User user(User u) { return new Responses.User(u.getUserId(), u.getUserName(), u.getUserEmail(), u.getUserPhone(), u.getUserRole()); }
    public Responses.Category category(Category c) {
        return new Responses.Category(c.getCategoryId(), c.getCategoryName(), c.getCategorySlug(),
                c.getCategoryDescription(), c.getCategoryParent() == null ? null : c.getCategoryParent().getCategoryId());
    }
    public Responses.Product product(Product p) {
        var category = p.getProductCategory() == null ? null : category(p.getProductCategory());
        var imageList = images.findByProductImageProduct_ProductIdOrderByProductImageSortOrderAscProductImageIdAsc(p.getProductId())
                .stream().map(i -> new Responses.Image(i.getProductImageUrl(), i.getProductImageAltText(),
                        i.isProductImageIsPrimary(), i.getProductImageSortOrder())).toList();
        var variantList = variants.findByProductVariantProduct_ProductIdAndProductVariantStatus(p.getProductId(), "ACTIVE")
                .stream().map(v -> new Responses.Variant(v.getProductVariantId(), v.getProductVariantSku(),
                        v.getProductVariantColor(), v.getProductVariantSize(), v.getProductVariantPriceOverride(),
                        v.getProductVariantStockQuantity())).toList();
        return new Responses.Product(p.getProductId(), p.getProductName(), p.getProductSlug(), p.getProductDescription(),
                p.getProductPrice(), p.getProductSalePrice(), p.getProductStatus(), p.isProductIsFeatured(),
                p.isProductIsFlashSale(), category, imageList, variantList);
    }
    public Responses.Address address(Address a) {
        return new Responses.Address(a.getAddressId(), a.getAddressRecipientName(), a.getAddressPhone(), a.getAddressDetail(),
                a.getAddressWard(), a.getAddressDistrict(), a.getAddressCity(), a.getAddressPostalCode(), a.isAddressIsDefault());
    }
    public Responses.CartLine cartLine(CartItem i) {
        ProductVariant v=i.getCartItemProductVariant(); Product p=v.getProductVariantProduct();
        BigDecimal price=v.getProductVariantPriceOverride()!=null?v.getProductVariantPriceOverride():
                (p.getProductSalePrice()!=null?p.getProductSalePrice():p.getProductPrice());
        String image=images.findByProductImageProduct_ProductIdAndProductImageIsPrimaryTrue(p.getProductId())
                .map(ProductImage::getProductImageUrl).orElse(null);
        String variant=String.join(" / ", java.util.stream.Stream.of(v.getProductVariantColor(),v.getProductVariantSize()).filter(s->s!=null&&!s.isBlank()).toList());
        return new Responses.CartLine(i.getCartItemId(),v.getProductVariantId(),p.getProductId(),p.getProductName(),image,
                v.getProductVariantSku(),v.getProductVariantColor(),v.getProductVariantSize(),price,i.getCartItemQuantity(),
                v.getProductVariantStockQuantity(),price.multiply(BigDecimal.valueOf(i.getCartItemQuantity())));
    }
    public Responses.Order order(Order o, List<OrderItem> lines) {
        var mapped=lines.stream().map(i->new Responses.OrderLine(i.getOrderItemId(),i.getOrderItemProductName(),
                i.getOrderItemSkuSnapshot(),i.getOrderItemVariantSnapshot(),i.getOrderItemUnitPrice(),
                i.getOrderItemQuantity(),i.getOrderItemLineTotal())).toList();
        return new Responses.Order(o.getOrderId(),o.getOrderCode(),o.getOrderStatus(),o.getOrderPaymentMethod(),
                o.getOrderPaymentStatus(),o.getOrderSubtotal(),o.getOrderDiscountAmount(),o.getOrderShippingFee(),
                o.getOrderTotalAmount(),o.getOrderRecipientName(),o.getOrderRecipientPhone(),o.getOrderShippingAddress(),
                o.getOrderCreatedAt(),mapped);
    }
}
