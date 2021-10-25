package com.itda.apiserver;

import com.itda.apiserver.domain.*;
import com.itda.apiserver.dto.*;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static AddproductRequestDto createAddProductRequestDto(Long categoryId) {
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
        addproductRequestDto.setMainCategoryId(categoryId);

        return addproductRequestDto;
    }

    public static AddReviewRequestDto createAddReviewRequestDto() {

        AddReviewRequestDto addReviewRequest = new AddReviewRequestDto();

        List<String> imageList = new ArrayList<>();
        imageList.add("image url1");
        imageList.add("image url2");

        addReviewRequest.setContents("신선하고 맛있어요");
        addReviewRequest.setImages(imageList);

        return addReviewRequest;
    }

    public static SignUpRequestDto createSignUpRequestDto() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setTelephone("01012345678");
        signUpRequestDto.setEmail("test@naver.com");
        signUpRequestDto.setName("roach");
        signUpRequestDto.setPassword("1234");
        return signUpRequestDto;
    }

    public static User returnUserEntity() {
        return User.builder()
                .name("roach")
                .email("honux")
                .phone("01000000000")
                .role(Role.SELLER)
                .password("1234@@@")
                .account("110-440-1104123")
                .build();
    }

    public static MainCategory createMainCategory() {
        return new MainCategory("TEST_CATEGORY");
    }

    public static ShippingInfoDto createShippingInfoDte() {
        ShippingInfoDto shippingInfoDto = new ShippingInfoDto();
        shippingInfoDto.setConsignee("크롱");
        shippingInfoDto.setPhone("01011112222");
        shippingInfoDto.setRegionOneDepthName("서울특별시");
        shippingInfoDto.setRegionTwoDepthName("강남구");
        shippingInfoDto.setRegionThreeDepthName("역삼동");
        shippingInfoDto.setMainBuildingNo(40);
        shippingInfoDto.setSubBuildingNo(4);
        shippingInfoDto.setZoneNo(36680);
        shippingInfoDto.setDefaultAddrYn(true);
        shippingInfoDto.setMessage("문 앞에 놓고 전화주세요");

        return shippingInfoDto;
    }

    public static Product createProduct() {

        return Product.builder()
                .title("맛있는 사과")
                .price(10000)
                .deliveryFee(3000)
                .account("111-222-333")
                .accountHolder("김나연")
                .bank("우리은행")
                .capacity("1kg")
                .description("<p>맛있어요!</p>")
                .origin("국산")
                .packageType("박스")
                .salesUnit("1박스")
                .build();
    }

    public static ShippingInfo createShippingInfo(User user) {
        Address address = new Address("서울특별시", "강남구", "역삼동", 40, 4, 12345);
        return new ShippingInfo(address, user, "김나연", "문 앞에 놔주세요", "010-1111-2222", true);
    }
}
