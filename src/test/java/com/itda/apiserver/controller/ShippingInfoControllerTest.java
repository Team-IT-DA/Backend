package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.itda.apiserver.TestHelper.createShippingInfoDte;
import static com.itda.apiserver.TestHelper.returnUserEntity;
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

        mockMvc.perform(post("/api/shippingInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(shippingInfoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.shippingAddressId").isNumber())
                .andDo(print());
    }

}
