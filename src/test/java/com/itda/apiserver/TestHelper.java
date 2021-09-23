package com.itda.apiserver;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static AddproductRequestDto createAddProductRequestDto() {
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

    public static MainCategory createMainCategory() {
        return new MainCategory("TEST_CATEGORY");
    }

}
