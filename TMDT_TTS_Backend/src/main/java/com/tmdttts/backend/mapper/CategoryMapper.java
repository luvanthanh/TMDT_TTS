package com.tmdttts.backend.mapper;

import com.tmdttts.backend.dto.response.category.CategoryResponse;
import com.tmdttts.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        Long parentId = category.getCategoryParent() == null ? null : category.getCategoryParent().getCategoryId();
        return new CategoryResponse(category.getCategoryId(), category.getCategoryName(), category.getCategorySlug(),
                category.getCategoryDescription(), parentId);
    }
}
