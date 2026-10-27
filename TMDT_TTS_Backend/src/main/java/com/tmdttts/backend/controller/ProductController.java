package com.tmdttts.backend.controller;
import com.tmdttts.backend.entity.Product;
import com.tmdttts.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/products")
public class ProductController {
 private final ProductRepository repository;
 public ProductController(ProductRepository repository){this.repository=repository;}
 @GetMapping public List<Product> getActive(@RequestParam(defaultValue="") String keyword,@RequestParam(required=false) Long categoryId){
  if(categoryId!=null)return repository.findByProductCategory_CategoryIdAndProductStatus(categoryId,"ACTIVE");
  if(!keyword.isBlank())return repository.findByProductNameContainingIgnoreCaseAndProductStatus(keyword,"ACTIVE");
  return repository.findByProductStatus("ACTIVE");
 }
 @GetMapping("/{id}") public Product getById(@PathVariable Long id){Product p=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));if(!"ACTIVE".equals(p.getProductStatus()))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm");return p;}
 @GetMapping("/slug/{slug}") public Product getBySlug(@PathVariable String slug){return repository.findByProductSlug(slug).filter(p->"ACTIVE".equals(p.getProductStatus())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));}
}
