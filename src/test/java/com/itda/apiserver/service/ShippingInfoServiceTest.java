package com.itda.apiserver.service;

import com.itda.apiserver.domain.Address;
import com.itda.apiserver.domain.ShippingInfo;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.repository.ShippingInfoCustomRepository;
import com.itda.apiserver.repository.ShippingInfoRepository;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShippingInfoServiceTest {

    @Autowired
    private ShippingInfoService shippingInfoService;

    @MockBean
    private ShippingInfoRepository shippingInfoRepository;

    @MockBean
    private ShippingInfoCustomRepository shippingInfoCustomRepository;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private ShippingInfoDto shippingInfoDto;

    @Mock
    private ShippingInfo shippingInfo;

    @Mock
    private Address address;


    @Test
    @DisplayName("배송지 입력 기능 테스트, 배송지 request가 default인 경우 기존의 default 배송지 상태를 변경한다.")
    void addDefaultShippingInfo() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(shippingInfoDto.isDefaultAddrYn()).thenReturn(true);
        when(shippingInfoRepository.existsByUserIdAndDefaultYNTrue(anyLong())).thenReturn(true);
        when(shippingInfoRepository.findByUserIdAndDefaultYNTrue(anyLong())).thenReturn(Optional.of(shippingInfo));

        shippingInfoService.addShippingInfo(1L, shippingInfoDto);

        verify(shippingInfoRepository, times(1)).existsByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfoRepository, times(1)).findByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfo, times(1)).beNotDefault();
        verify(userRepository, times(1)).findById(anyLong());
        verify(shippingInfoRepository, times(1)).save(any(ShippingInfo.class));
    }

    @Test
    @DisplayName("배송지 request가 default가 아닌 경우, 배송지 입력 테스트")
    void addShippingInfo() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(shippingInfoDto.isDefaultAddrYn()).thenReturn(false);
        when(shippingInfoRepository.existsByUserIdAndDefaultYNTrue(anyLong())).thenReturn(false);

        shippingInfoService.addShippingInfo(1L, shippingInfoDto);

        verify(shippingInfoRepository, times(0)).existsByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfoRepository, times(0)).findByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfo, times(0)).beNotDefault();
        verify(userRepository, times(1)).findById(anyLong());
        verify(shippingInfoRepository, times(1)).save(any(ShippingInfo.class));
    }

    @Test
    @DisplayName("배송지 조회 테스트")
    void getShowShippingInfoDto() {

        when(shippingInfoRepository.existsByUserIdAndDefaultYNTrue(anyLong())).thenReturn(true);
        when(shippingInfoRepository.findByUserIdAndDefaultYNTrue(anyLong())).thenReturn(Optional.of(shippingInfo));
        when(shippingInfo.getAddress()).thenReturn(address);
        when(shippingInfoCustomRepository.findRecentById(anyLong())).thenReturn(List.of(shippingInfo));

        shippingInfoService.getShowShippingInfoDto(1L);

        verify(shippingInfoRepository, times(1)).existsByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfoRepository, times(1)).findByUserIdAndDefaultYNTrue(anyLong());
        verify(shippingInfoCustomRepository, times(1)).findRecentById(anyLong());
    }
}
