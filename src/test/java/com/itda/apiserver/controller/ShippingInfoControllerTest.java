package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.ShippingInfo;
import com.itda.apiserver.domain.User;
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

import javax.persistence.EntityManager;

import static com.itda.apiserver.TestHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private EntityManager em;

    @Autowired
    private ShippingInfoService shippingInfoService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("컨트롤러의 배송지 추가 기능 테스트")
    void addShippingInfo() throws Exception {

        ShippingInfoDto shippingInfoDto = createShippingInfoDte();
        User user = returnUserEntity();
        em.persist(user);

        shippingInfoService.addShippingInfo(user.getId(), shippingInfoDto);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(post("/api/shippingInfos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(shippingInfoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.shippingAddressId").isNumber())
                .andDo(print());
    }

    @Test
    @DisplayName("기본 배송지와 최근 배송지 조회 테스트")
    void showShippingInfo() throws Exception {

        User user = returnUserEntity();
        em.persist(user);

        ShippingInfo shippingInfo = createShippingInfo(user);
        em.persist(shippingInfo);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(get("/api/shippingInfos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.defaultShippingAddress.id").isNumber())
                .andExpect(jsonPath("$.data.shippingAddress").isArray())
                .andDo(print());
    }
}
