package com.itda.apiserver.repository;

import com.itda.apiserver.domain.ShippingInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.itda.apiserver.domain.QOrderSheet.orderSheet;
import static com.itda.apiserver.domain.QShippingInfo.shippingInfo;

@Repository
@RequiredArgsConstructor
public class ShippingInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ShippingInfo> findRecentById(Long userId) {
        return jpaQueryFactory.selectFrom(shippingInfo)
                .where(shippingInfo.user.id.eq(userId))
                .innerJoin(orderSheet)
                .on(orderSheet.shippingInfo.id.eq(shippingInfo.id))
                .orderBy(orderSheet.createdAt.desc())
                .limit(4)
                .fetch();
    }
}
