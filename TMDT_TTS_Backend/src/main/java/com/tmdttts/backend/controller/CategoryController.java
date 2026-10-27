package com.tmdttts.backend.controller;
import com.tmdttts.backend.entity.Category;
import com.tmdttts.backend.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/categories")
public class CategoryController {
 private final CategoryRepository repository;
 public CategoryController(CategoryRepository repository){this.repository=repository;}
 @GetMapping public List<Category> getActive(){return repository.findByCategoryStatus("ACTIVE");}
 @GetMapping("/{id}") public Category getById(@PathVariable Long id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy danh mục"));}
}
