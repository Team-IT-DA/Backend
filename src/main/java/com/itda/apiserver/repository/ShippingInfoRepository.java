package com.itda.apiserver.repository;

import com.itda.apiserver.domain.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {
}
