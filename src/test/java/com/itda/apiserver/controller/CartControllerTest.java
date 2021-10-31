package com.itda.apiserver.controller;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.service.CartService;
import com.itda.apiserver.service.CategoryService;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import java.util.List;

import static com.itda.apiserver.TestHelper.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryService categoryService;

    User user;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        MainCategory mainCategory = categoryService.addCategory("채소");

        AddproductRequestDto addProductRequestDto = createAddProductRequestDto(mainCategory.getId());
        user = userService.signUp(createSignUpRequestDto());
        productService.addProduct(addProductRequestDto, user.getId());
    }

    @Test
    @DisplayName("유저 카드에 상품이 잘 추가되는지 확인합니다.")
    void testAddProductToCart() {
        Product product = productService.getProductsBySellerName(user.getName()).get(0);
        CartProduct cartProductDto = createCartProductDto(1, product.getId());
        cartService.addProduct(cartProductDto, user.getId());
    }

}