package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.CategoryRequest;
import com.poly.coffee.dto.response.CategoryResponse;
import com.poly.coffee.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
