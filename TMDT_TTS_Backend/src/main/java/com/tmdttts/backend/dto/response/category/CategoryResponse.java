package com.tmdttts.backend.dto.response.category;

public record CategoryResponse(Long id, String name, String slug, String description, Long parentId) {}
