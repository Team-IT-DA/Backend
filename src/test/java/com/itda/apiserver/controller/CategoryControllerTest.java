package com.itda.apiserver.controller;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.jwt.TokenExtractor;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import(value = {TokenProvider.class, TokenExtractor.class})
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("전체 카테고리 조회 기능 테스트")
    void showAllCategories() throws Exception {

        List<MainCategory> mainCategoryList = new ArrayList<>();
        mainCategoryList.add(new MainCategory("채소"));
        mainCategoryList.add(new MainCategory("과일/견과/쌀"));
        mainCategoryList.add(new MainCategory("수산/해산/건어물"));
        mainCategoryList.add(new MainCategory("정육/계란"));

        Mockito.when(categoryService.getCategories()).thenReturn(mainCategoryList);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.categories").isArray());
    }
}
