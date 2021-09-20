package com.itda.apiserver.repository;

import com.itda.apiserver.domain.OrderSheet;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.itda.apiserver.domain.QOrderSheet.orderSheet;

@Repository
@RequiredArgsConstructor
public class OrderSheetCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OrderSheet> findByUserId(Long userId, Integer period, Pageable pageable) {
        return jpaQueryFactory.selectFrom(orderSheet)
                .where(orderSheet.user.id.eq(userId),
                        afterDate(period))
                .orderBy(getOrderSpecifier(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        Order direction = pageable.getSort().getOrderFor("createdAt").isDescending() ?
                Order.DESC : Order.ASC;
        return new OrderSpecifier(direction, orderSheet);
    }

    private BooleanExpression afterDate(Integer period) {
        if (period != null) {
            LocalDateTime date = LocalDateTime.now().minusMonths(period);
            return orderSheet.createdAt.after(date);
        }
        return null;
    }
}
