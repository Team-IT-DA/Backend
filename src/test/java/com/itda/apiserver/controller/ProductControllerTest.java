package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.UserRepository;
import com.itda.apiserver.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.itda.apiserver.TestHelper.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ProductService productService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    MainCategoryRepository mainCategoryRepository;

    @Test
    @DisplayName("물품 추가 성공시 HTTP REQUEST 200 반환 e2e Testing")
    void addProductE2ETest() throws Exception {

        MainCategory mainCategory = new MainCategory("채소");

        User user = User.builder()
                .name("roach")
                .email("honux")
                .phone("01000000000")
                .role(Role.SELLER)
                .password("1234@@@")
                .account("110-440-1104123")
                .build();

        String token = "Bearer " + tokenProvider.createToken(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mainCategoryRepository.findById(anyLong())).thenReturn(Optional.of(mainCategory));

        AddproductRequestDto addproductRequestDto = createAddProductRequestDto();

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

        User user = User.builder()
                .name("roach")
                .email("honux")
                .phone("01000000000")
                .role(Role.SELLER)
                .password("1234@@@")
                .account("110-440-1104123")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mainCategoryRepository.findById(anyLong())).thenReturn(Optional.of(mainCategory));

        for(int i = 0; i < 20; i++) {
            productService.addProduct(createAddProductRequestDto(), 1L);
        }

        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(11)))
                .andDo(print());

    }
}