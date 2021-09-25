package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.ShippingInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShippingInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    private ShippingInfoService shippingInfoService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("컨트롤러의 배송지 추가 기능 테스트")
    void addShippingInfo() throws Exception {

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


        Long shippingAddressId = 1L;
        shippingInfoService.addShippingInfo(1L, shippingInfoDto);

        String token = "Bearer " + tokenProvider.createToken(1L);

        mockMvc.perform(post("/api/shippingInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(shippingInfoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.shippingAddressId").isNumber())
                .andDo(print());
    }

}
