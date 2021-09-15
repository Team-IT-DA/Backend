package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenProvider tokenProvider;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProductRepository productRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("리뷰 추가 기능 테스트")
    void addReview() throws Exception {

        AddReviewRequestDto addReviewRequest = createAddReviewRequestDto();
        String token = "Bearer " + tokenProvider.createToken(1L);
        Product product = Product.builder()
                .title("맛있는 사과")
                .price(10000)
                .build();

        User user = User.builder()
                .name("roach")
                .email("honux")
                .phone("01000000000")
                .role(Role.SELLER)
                .password("1234@@@")
                .account("110-440-1104123")
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/products/1/review")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addReviewRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private AddReviewRequestDto createAddReviewRequestDto() {

        AddReviewRequestDto addReviewRequest = new AddReviewRequestDto();

        List<String> imageList = new ArrayList<>();
        imageList.add("image url1");
        imageList.add("image url2");

        addReviewRequest.setContents("신선하고 맛있어요");
        addReviewRequest.setImages(imageList);

        return addReviewRequest;
    }
}
