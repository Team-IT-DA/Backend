package com.itda.apiserver.querydsl;

import com.itda.apiserver.domain.*;
import com.itda.apiserver.repository.OrderSheetCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderSheetCustomRepositoryTest {

    @Autowired
    private OrderSheetCustomRepository customRepository;

    @Autowired
    private EntityManager em;

    private User user;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .name("김나연")
                .role(Role.USER)
                .password("password")
                .phone("010-1111-2222")
                .email("yeon@gmail.com")
                .build();
        em.persist(user);


        for (long i = 0; i < 5; i++) {
            OrderSheet orderSheet = createOrderSheet(user);
            orderSheet.setCore(LocalDateTime.now().minusMonths(1));
        }
    }

    private OrderSheet createOrderSheet(User user) {

        Product product1 = getProduct("맛있는 사과");
        Product product2 = getProduct("충청도 곶감");

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(2, product1));
        orders.add(new Order(3, product2));

        ShippingInfo shippingInfo = getShippingInfo(user);

        OrderSheet orderSheet = OrderSheet.createOrderSheet(56000, user, shippingInfo, orders);

        em.persist(orderSheet);

        return orderSheet;
    }


    private Product getProduct(String title) {
        Product product = Product.builder()
                .title(title)
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

        em.persist(product);
        return product;
    }

    private ShippingInfo getShippingInfo(User user) {
        Address address = new Address("서울특별시", "강남구", "역삼동", 40, 4, 12345);
        ShippingInfo shippingInfo = new ShippingInfo(address, user, "김나연", "문 앞에 놔주세요", "010-1111-2222", false);
        em.persist(shippingInfo);
        return shippingInfo;
    }


    @Test
    @DisplayName("기간별로 조회가 잘 되는지 확인한다.")
    void sort() {

        List<OrderSheet> orderSheets =
                customRepository.findByUserId(user.getId(), 3, PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt"));

        assertThat(orderSheets).allMatch(orderSheet -> orderSheet.getCreatedAt().isAfter(LocalDateTime.now().minusMonths(3)));
    }

}
