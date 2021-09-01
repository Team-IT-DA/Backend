package com.itda.apiserver.service;

import com.itda.apiserver.domain.OrderHistory;
import com.itda.apiserver.domain.OrderSheet;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.OrderProductRequest;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.exception.OrderDuplicationException;
import com.itda.apiserver.repository.OrderHistoryRepository;
import com.itda.apiserver.repository.OrderSheetRepository;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderHistoryRepository orderHistoryRepository;

    @MockBean
    private OrderSheetRepository orderSheetRepository;

    @MockBean
    private OrderValidationService orderValidationService;

    @Mock
    private User user;

    @Mock
    private OrderRequestDto orderRequest;

    @Mock
    private ShippingInfoDto shippingInfoRequest;

    @Mock
    private List<OrderProductRequest> orderProductList;

    @Test
    @DisplayName("주문 기능 테스트")
    void order() {

        when(orderValidationService.isDuplicatedOrder()).thenReturn(false);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRequest.getShippingAddress()).thenReturn(shippingInfoRequest);
        when(orderRequest.getOrderList()).thenReturn(orderProductList);

        orderService.order(1L, orderRequest);

        verify(orderValidationService, times(1)).isDuplicatedOrder();
        verify(orderSheetRepository, times(1)).save(any(OrderSheet.class));
        verify(orderHistoryRepository, times(1)).save(any(OrderHistory.class));
        verify(orderValidationService, times(1)).save(anyLong());

    }

    @Test
    @DisplayName("주문 요청이 중복으로 들어오면 예외 발생")
    void duplicatedOrder() {

        when(orderValidationService.isDuplicatedOrder()).thenReturn(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRequest.getShippingAddress()).thenReturn(shippingInfoRequest);
        when(orderRequest.getOrderList()).thenReturn(orderProductList);

        assertThrows(OrderDuplicationException.class, () -> orderService.order(1L, orderRequest));

        verify(orderValidationService, times(1)).isDuplicatedOrder();
        verify(orderSheetRepository, times(0)).save(any(OrderSheet.class));
        verify(orderHistoryRepository, times(0)).save(any(OrderHistory.class));
        verify(orderValidationService, times(0)).save(anyLong());
    }
}
