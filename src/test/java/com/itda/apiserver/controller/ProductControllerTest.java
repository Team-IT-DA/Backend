package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    ProductRepository productRepository;

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

        AddproductRequestDto addproductRequestDto = new AddproductRequestDto();

        addproductRequestDto.setName("유기농 감자 1박스");
        addproductRequestDto.setSubTitle("유가농 감자와 생선까지");
        addproductRequestDto.setPrice(20000);
        addproductRequestDto.setSalesUnit("2 박스씩 판매");
        addproductRequestDto.setProductImage("https://www.naver.com");
        addproductRequestDto.setCapacity("2kg");
        addproductRequestDto.setDeliveryFee(2500);
        addproductRequestDto.setDeliveryFeeCondition("산지 / 제주는 3000원");
        addproductRequestDto.setOrigin("제주도");
        addproductRequestDto.setPackagingType("박스");
        addproductRequestDto.setNotice("뜯을 시 하루안에 드시는 것이 좋습니다.");
        addproductRequestDto.setDescription("<div>'Hello World'</div>");
        addproductRequestDto.setBank("국민은행");
        addproductRequestDto.setAccountHolder("호눅스");
        addproductRequestDto.setAccount("110-440-114123");
        addproductRequestDto.setMainCategoryId(1L);

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

        for (int i = 0; i < 20; i++) {
            productService.addProduct(createAddProductRequestDto(), 1L);
        }

        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(11)))
                .andDo(print());

    }


    private AddproductRequestDto createAddProductRequestDto() {
        AddproductRequestDto addproductRequestDto = new AddproductRequestDto();

        addproductRequestDto.setName("유기농 감자 1박스");
        addproductRequestDto.setSubTitle("유가농 감자와 생선까지");
        addproductRequestDto.setPrice(20000);
        addproductRequestDto.setSalesUnit("2 박스씩 판매");
        addproductRequestDto.setProductImage("https://www.naver.com");
        addproductRequestDto.setCapacity("2kg");
        addproductRequestDto.setDeliveryFee(2500);
        addproductRequestDto.setDeliveryFeeCondition("산지 / 제주는 3000원");
        addproductRequestDto.setOrigin("제주도");
        addproductRequestDto.setPackagingType("박스");
        addproductRequestDto.setNotice("뜯을 시 하루안에 드시는 것이 좋습니다.");
        addproductRequestDto.setDescription("<div>'Hello World'</div>");
        addproductRequestDto.setBank("국민은행");
        addproductRequestDto.setAccountHolder("호눅스");
        addproductRequestDto.setAccount("110-440-114123");
        addproductRequestDto.setMainCategoryId(1L);

        return addproductRequestDto;
    }

    @Test
    @DisplayName("제품 추가 기능 테스트")
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
