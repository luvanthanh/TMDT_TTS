package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.response.product.ProductVariantResponse;
import com.tmdttts.backend.dto.response.product.ProductImageResponse;
import com.tmdttts.backend.dto.response.product.ProductResponse;

import com.tmdttts.backend.entity.Product;
import com.tmdttts.backend.repository.ProductImageRepository;
import com.tmdttts.backend.repository.ProductVariantRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ProductImageRepository images;
    private final ProductVariantRepository variants;
    private final CategoryMapper categoryMapper;
    public ProductMapper(ProductImageRepository images, ProductVariantRepository variants, CategoryMapper categoryMapper) {
        this.images=images; this.variants=variants; this.categoryMapper=categoryMapper;
    }
    public ProductResponse toResponse(Product product) {
        var category=product.getProductCategory()==null?null:categoryMapper.toResponse(product.getProductCategory());
        var imageList=images.findByProductImageProduct_ProductIdOrderByProductImageSortOrderAscProductImageIdAsc(product.getProductId())
                .stream().map(i->new ProductImageResponse(i.getProductImageUrl(),i.getProductImageAltText(),i.isProductImageIsPrimary(),i.getProductImageSortOrder())).toList();
        var variantList=variants.findByProductVariantProduct_ProductIdAndProductVariantStatus(product.getProductId(),"ACTIVE")
                .stream().map(v->new ProductVariantResponse(v.getProductVariantId(),v.getProductVariantSku(),v.getProductVariantColor(),v.getProductVariantSize(),v.getProductVariantPriceOverride(),v.getProductVariantStockQuantity())).toList();
        return new ProductResponse(product.getProductId(),product.getProductName(),product.getProductSlug(),product.getProductDescription(),
                product.getProductPrice(),product.getProductSalePrice(),product.getProductStatus(),product.isProductIsFeatured(),
                product.isProductIsFlashSale(),category,imageList,variantList);
    }
}
