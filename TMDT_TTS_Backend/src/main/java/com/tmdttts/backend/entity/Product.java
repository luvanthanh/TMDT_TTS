package com.tmdttts.backend.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="product_id") private Long productId;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_category_id") private Category productCategory;
 @Column(name="product_name",nullable=false,length=255) private String productName;
 @Column(name="product_slug",nullable=false,unique=true,length=280) private String productSlug;
 @Column(name="product_description") private String productDescription;
 @Column(name="product_price",nullable=false,precision=12,scale=2) private BigDecimal productPrice;
 @Column(name="product_sale_price",precision=12,scale=2) private BigDecimal productSalePrice;
 @Column(name="product_status",nullable=false,length=20) private String productStatus="DRAFT";
 @Column(name="product_is_featured",nullable=false) private boolean productIsFeatured;
 @Column(name="product_is_flash_sale",nullable=false) private boolean productIsFlashSale;
 @Column(name="product_created_at",nullable=false) private OffsetDateTime productCreatedAt;
 @Column(name="product_updated_at",nullable=false) private OffsetDateTime productUpdatedAt;
 @PrePersist void prePersist(){var now=OffsetDateTime.now();if(productCreatedAt==null)productCreatedAt=now;if(productUpdatedAt==null)productUpdatedAt=now;}
 @PreUpdate void preUpdate(){productUpdatedAt=OffsetDateTime.now();}
 public Long getProductId(){return productId;} public Category getProductCategory(){return productCategory;} public void setProductCategory(Category v){productCategory=v;}
 public String getProductName(){return productName;} public void setProductName(String v){productName=v;} public String getProductSlug(){return productSlug;} public void setProductSlug(String v){productSlug=v;}
 public String getProductDescription(){return productDescription;} public void setProductDescription(String v){productDescription=v;} public BigDecimal getProductPrice(){return productPrice;} public void setProductPrice(BigDecimal v){productPrice=v;}
 public BigDecimal getProductSalePrice(){return productSalePrice;} public void setProductSalePrice(BigDecimal v){productSalePrice=v;} public String getProductStatus(){return productStatus;} public void setProductStatus(String v){productStatus=v;}
 public boolean isProductIsFeatured(){return productIsFeatured;} public void setProductIsFeatured(boolean v){productIsFeatured=v;} public boolean isProductIsFlashSale(){return productIsFlashSale;} public void setProductIsFlashSale(boolean v){productIsFlashSale=v;}
 public OffsetDateTime getProductCreatedAt(){return productCreatedAt;} public OffsetDateTime getProductUpdatedAt(){return productUpdatedAt;}
}
