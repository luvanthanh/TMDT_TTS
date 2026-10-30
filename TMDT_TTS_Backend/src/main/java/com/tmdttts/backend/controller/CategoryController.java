package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.response.category.CategoryResponse;

import com.tmdttts.backend.mapper.CategoryMapper;
import com.tmdttts.backend.repository.CategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController @RequestMapping("/api/categories") @Transactional(readOnly=true)
public class CategoryController {
    private final CategoryRepository repository; private final CategoryMapper mapper;
    public CategoryController(CategoryRepository repository, CategoryMapper mapper){this.repository=repository;this.mapper=mapper;}
    @GetMapping public List<CategoryResponse> getActive(){return repository.findByCategoryStatus("ACTIVE").stream().map(mapper::toResponse).toList();}
    @GetMapping("/{id}") public CategoryResponse getById(@PathVariable Long id){return repository.findById(id)
            .filter(c->"ACTIVE".equals(c.getCategoryStatus())).map(mapper::toResponse)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy danh mục"));}
}
