package com.itda.apiserver.controller;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.service.CartService;
import com.itda.apiserver.service.CategoryService;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.itda.apiserver.TestHelper.*;

@SpringBootTest
@Transactional
class CartControllerTest {

    /**
     * Service Layer
     */
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryService categoryService;

    /**
     * Domain
     */
    User user;

    @BeforeEach
    void setUp() {
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

    @Test
    @DisplayName("배열로 넣어도 잘 수정되는지 확인합니다.")
    void testAllProductsAddToCart() {

        List<CartProduct> cartProducts = new ArrayList<>();

        Product product = productService.getProductsBySellerName(user.getName()).get(0);
        CartProduct cartProductDto = createCartProductDto(1, product.getId());

        cartProducts.add(cartProductDto);

        cartService.addProducts(cartProducts, user.getId());
    }

}