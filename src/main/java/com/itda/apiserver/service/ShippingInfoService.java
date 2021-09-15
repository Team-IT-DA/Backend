package com.itda.apiserver.service;

import com.itda.apiserver.domain.Address;
import com.itda.apiserver.domain.ShippingInfo;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.exception.ShippingInfoNotFoundException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.repository.ShippingInfoRepository;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShippingInfoService {

    private final ShippingInfoRepository shippingInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addShippingInfo(Long userId, ShippingInfoDto shippingInfoDto) {

        if (shippingInfoDto.isDefaultAddrYn() && isDefaultShippingInfoExist(userId)) {
            ShippingInfo defaultShippingInfo = shippingInfoRepository.findByUserIdAndDefaultYNTrue(userId)
                    .orElseThrow(ShippingInfoNotFoundException::new);
            defaultShippingInfo.beNotDefault();
        }

        Address address = new Address(shippingInfoDto.getRegionOneDepthName(), shippingInfoDto.getRegionTwoDepthName(),
                shippingInfoDto.getRegionThreeDepthName(), shippingInfoDto.getMainBuildingNo(),
                shippingInfoDto.getSubBuildingNo(), shippingInfoDto.getZoneNo());

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        ShippingInfo shippingInfo = new ShippingInfo(address, user, shippingInfoDto.getConsignee(),
                shippingInfoDto.getMessage(), shippingInfoDto.getPhone(), shippingInfoDto.isDefaultAddrYn());

        shippingInfoRepository.save(shippingInfo);

        return shippingInfo.getId();
    }

    private boolean isDefaultShippingInfoExist(Long userId) {
        return shippingInfoRepository.existsByUserIdAndDefaultYNTrue(userId);
    }
}
