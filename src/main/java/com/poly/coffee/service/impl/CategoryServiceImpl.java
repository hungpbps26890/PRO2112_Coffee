package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.CategoryRequest;
import com.poly.coffee.dto.response.CategoryResponse;
import com.poly.coffee.entity.Category;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.CategoryMapper;
import com.poly.coffee.repository.CategoryRepository;
import com.poly.coffee.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toCategoryResponse(findCategoryById(id));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findCategoryById(id);

        categoryMapper.updateCategory(category, request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        findCategoryById(id);

        categoryRepository.deleteById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
