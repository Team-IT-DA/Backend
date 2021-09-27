package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.itda.apiserver.TestHelper.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ProductService productService;

    @Autowired
    MainCategoryRepository mainCategoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("물품 추가 성공시 HTTP REQUEST 200 반환 e2e Testing")
    void addProductE2ETest() throws Exception {

        MainCategory mainCategory = new MainCategory("채소");
        User user = userService.signUp(createSignUpRequestDto());
        String token = "Bearer " + tokenProvider.createToken(user.getId());
        MainCategory category = mainCategoryRepository.save(mainCategory);

        AddproductRequestDto addproductRequestDto = createAddProductRequestDto(category.getId());

        mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(addproductRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("페이지 네이션이 잘되는지 확인합니다.")
    void getProductE2ETest() throws Exception {


        MainCategory mainCategory = new MainCategory("채소");

        User user = userService.signUp(createSignUpRequestDto());
        MainCategory category = mainCategoryRepository.save(mainCategory);

        for (int i = 0; i < 20; i++) {
            productService.addProduct(createAddProductRequestDto(category.getId()), user.getId());
        }

        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(11)))
                .andDo(print());

    }

    @Test
    @DisplayName("제품 상세 조회 기능 테스트")
    void showDetailProduct() throws Exception {

        MainCategory mainCategory = new MainCategory("채소");

        MainCategory category = mainCategoryRepository.save(mainCategory);
        User user = userService.signUp(createSignUpRequestDto());
        productService.addProduct(createAddProductRequestDto(category.getId()), user.getId());

        Long pageNum = productRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/products/" + pageNum))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product.name").isString())
                .andExpect(jsonPath("$.data.product.description").isString())
                .andExpect(jsonPath("$.data.product.price").isNumber())
                .andExpect(jsonPath("$.data.product.seller.name").isString())
                .andDo(print());

    }

    @Test
    @DisplayName("카테고리 별 제품 조회 테스트")
    void showProductsByCategory() throws Exception {

        MainCategory category = createMainCategory();
        mainCategoryRepository.save(category);

        User user = userService.signUp(createSignUpRequestDto());

        productService.addProduct(createAddProductRequestDto(category.getId()), user.getId());

        mockMvc.perform(get("/api/products")
                .param("category", category.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.products").isArray())
                .andExpect(jsonPath("$.data.products[0].id").isNumber())
                .andExpect(jsonPath("$.data.products[0].productName").isString())
                .andExpect(jsonPath("$.data.products[0].sellerName").isString())
                .andDo(print());
    }

}
