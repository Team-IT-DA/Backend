package com.itda.apiserver.repository;

import com.itda.apiserver.domain.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {

    Optional<ShippingInfo> findByUserIdAndDefaultYNTrue(Long userId);

    boolean existsByUserIdAndDefaultYNTrue(Long userId);
}
