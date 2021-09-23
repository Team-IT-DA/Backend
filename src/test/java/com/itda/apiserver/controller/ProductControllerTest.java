package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
import com.itda.apiserver.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

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

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ProductService productService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    MainCategoryRepository mainCategoryRepository;

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
    @DisplayName("제품 상세 조회 기능 테스트")
    void showDetailProduct() throws Exception {

        User user = createUser();
        MainCategory mainCategory = new MainCategory("채소");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mainCategoryRepository.findById(anyLong())).thenReturn(Optional.of(mainCategory));

        productService.addProduct(createAddProductRequestDto(), 1L);
        final Product product = productRepository.findAll().stream().findFirst().get();

        String url = "/api/products/" + Long.toString(product.getId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product.name").isString())
                .andExpect(jsonPath("$.data.product.description").isString())
                .andExpect(jsonPath("$.data.product.price").isNumber())
                .andExpect(jsonPath("$.data.product.seller.name").isString())
                .andDo(print());

    }

    private User createUser() {
        return User.builder()
                .name("roach")
                .email("honux")
                .phone("01000000000")
                .role(Role.SELLER)
                .password("1234@@@")
                .account("110-440-1104123")
                .build();
    }

    @Test
    @DisplayName("제품 이름으로 제품 검색 기능 테스트")
    void showProductsByName() throws Exception {

        User seller = createUser();
        Product product = createProduct(seller);
        productRepository.save(product);

        mockMvc.perform(get("/api/products")
                .param("productName", "당근"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.products").isArray())
                .andExpect(jsonPath("$.data.products[*].productName").value(product.getTitle()))
                .andDo(print());
    }

    private Product createProduct(User user) {
        return Product.builder()
                .title("흙당근")
                .description("제주 바다 바람을 품은 친환경 흙당근")
                .price(10000)
                .salesUnit("1봉지")
                .capacity("500g (2~4개입)")
                .deliveryFee(2500)
                .deliveryDescription("5만원 이상 무료배송")
                .origin("제주")
                .packageType("봉지")
                .description("###제목<br><p>내용들어가고 어쩌고</p>")
                .imageUrl("imgUrl.com")
                .account("111-222-333")
                .accountHolder("roach")
                .bank("우리은행")
                .seller(user)
                .build();
    }

    @Test
    @DisplayName("판매자 이름으로 제품 검색 기능 테스트")
    void showProductsBySellerName() throws Exception {

        User seller = createUser();
        Product product = createProduct(seller);
        productRepository.save(product);

        mockMvc.perform(get("/api/products")
                .param("sellerName", "roach"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.products").isArray())
                .andExpect(jsonPath("$.data.products[*].sellerName").value(seller.getName()))
                .andDo(print());
    }

}
