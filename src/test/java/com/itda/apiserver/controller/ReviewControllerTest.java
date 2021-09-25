package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.itda.apiserver.TestHelper.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ProductService productService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MainCategoryRepository mainCategoryRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("리뷰 추가 기능 테스트")
    void addReview() throws Exception {

        AddReviewRequestDto addReviewRequest = createAddReviewRequestDto();
        MainCategory mainCategory = new MainCategory("채소");
        User testUser = userRepository.save(returnUserEntity());
        MainCategory category = mainCategoryRepository.save(mainCategory);
        String token = "Bearer " + tokenProvider.createToken(testUser.getId());
        productService.addProduct(createAddProductRequestDto(category.getId()), testUser.getId());
        Product product = productRepository.findAll().get(0);


        mockMvc.perform(post("/api/products/"+product.getId()+"/reviews")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addReviewRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("내 리뷰만 조회 기능 테스트")
    void getMyReivewsTest() throws Exception {
        AddReviewRequestDto addReviewRequest = createAddReviewRequestDto();
        MainCategory mainCategory = new MainCategory("채소");
        User testUser = userRepository.save(returnUserEntity());
        MainCategory category = mainCategoryRepository.save(mainCategory);
        String token = "Bearer " + tokenProvider.createToken(testUser.getId());
        productService.addProduct(createAddProductRequestDto(category.getId()), testUser.getId());
        Product product = productRepository.findAll().get(0);
        reviewService.addReview(testUser.getId(), product.getId(), addReviewRequest);

        mockMvc.perform(
                get("/api/myPage/reviews")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(jsonPath("$.data[0].reviewDate").exists())
                .andExpect(jsonPath("$.data[0].productImage").isString())
                .andExpect(jsonPath("$.data[0].productName").isString())
                .andExpect(jsonPath("$.data[0].reviewImage").isString())
                .andExpect(status().isOk());
    }

}
