package com.itda.apiserver.repository;

import com.itda.apiserver.domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {

    @Query("select os from OrderSheet os" +
            " left join fetch os.orders o" +
            " left join fetch o.product p" +
            " left join fetch os.user u" +
            " where os.id = ?1")
    Optional<OrderSheet> findByIdWithOrders(Long orderSheetId);

}
