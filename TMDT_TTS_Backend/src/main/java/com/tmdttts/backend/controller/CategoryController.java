package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.mapper.ApiMapper;
import com.tmdttts.backend.repository.CategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController @RequestMapping("/api/categories") @Transactional(readOnly=true)
public class CategoryController {
    private final CategoryRepository repository; private final ApiMapper mapper;
    public CategoryController(CategoryRepository repository, ApiMapper mapper){this.repository=repository;this.mapper=mapper;}
    @GetMapping public List<Responses.Category> getActive(){return repository.findByCategoryStatus("ACTIVE").stream().map(mapper::category).toList();}
    @GetMapping("/{id}") public Responses.Category getById(@PathVariable Long id){return repository.findById(id)
            .filter(c->"ACTIVE".equals(c.getCategoryStatus())).map(mapper::category)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy danh mục"));}
}
