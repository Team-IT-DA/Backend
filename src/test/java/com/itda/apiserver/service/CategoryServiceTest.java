package com.itda.apiserver.service;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.repository.MainCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private MainCategoryRepository categoryRepository;

    @Mock
    private List<MainCategory> mainCategoryList;

    @Test
    @DisplayName("전체 카테고리 조회 기능 테스트")
    void getAllCategories() {

        when(categoryRepository.findAll()).thenReturn(mainCategoryList);

        categoryService.getCategories();

        verify(categoryRepository, times(1)).findAll();
    }
}
