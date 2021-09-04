package com.itda.apiserver.repository;

import com.itda.apiserver.domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
}
