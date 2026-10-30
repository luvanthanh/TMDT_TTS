package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.response.product.ProductResponse;

import com.tmdttts.backend.mapper.ProductMapper;
import com.tmdttts.backend.repository.ProductRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController @RequestMapping("/api/products") @Transactional(readOnly=true)
public class ProductController {
    private final ProductRepository repository; private final ProductMapper mapper;
    public ProductController(ProductRepository repository, ProductMapper mapper){this.repository=repository;this.mapper=mapper;}
    @GetMapping public List<ProductResponse> getActive(@RequestParam(defaultValue="") String keyword,
                                                         @RequestParam(required=false) Long categoryId) {
        List<com.tmdttts.backend.entity.Product> products;
        if(categoryId!=null&&!keyword.isBlank()) products=repository.findByProductCategory_CategoryIdAndProductNameContainingIgnoreCaseAndProductStatus(categoryId,keyword.trim(),"ACTIVE");
        else if(categoryId!=null) products=repository.findByProductCategory_CategoryIdAndProductStatus(categoryId,"ACTIVE");
        else if(!keyword.isBlank()) products=repository.findByProductNameContainingIgnoreCaseAndProductStatus(keyword.trim(),"ACTIVE");
        else products=repository.findByProductStatus("ACTIVE");
        return products.stream().map(mapper::toResponse).toList();
    }
    @GetMapping("/featured") public List<ProductResponse> featured(){return repository.findByProductIsFeaturedTrueAndProductStatus("ACTIVE").stream().map(mapper::toResponse).toList();}
    @GetMapping("/flash-sale") public List<ProductResponse> flashSale(){return repository.findByProductIsFlashSaleTrueAndProductStatus("ACTIVE").stream().map(mapper::toResponse).toList();}
    @GetMapping("/{id}") public ProductResponse getById(@PathVariable Long id){return repository.findById(id)
            .filter(p->"ACTIVE".equals(p.getProductStatus())).map(mapper::toResponse)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));}
    @GetMapping("/slug/{slug}") public ProductResponse getBySlug(@PathVariable String slug){return repository.findByProductSlug(slug)
            .filter(p->"ACTIVE".equals(p.getProductStatus())).map(mapper::toResponse)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));}
}
