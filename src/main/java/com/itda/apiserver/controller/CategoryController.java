package com.itda.apiserver.controller;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.CategoryResponseDto;
import com.itda.apiserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ApiResult<CategoryResponseDto> showAllCategories() {

        List<String> categoryNames = categoryService.getCategories().stream()
                .map(MainCategory::getName)
                .collect(Collectors.toList());

        return ApiResult.ok(new CategoryResponseDto(categoryNames));
    }
}
