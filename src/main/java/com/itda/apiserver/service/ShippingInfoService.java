package com.itda.apiserver.service;

import com.itda.apiserver.domain.Address;
import com.itda.apiserver.domain.ShippingInfo;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.ShippingAddressDto;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.dto.ShowShippingInfosDto;
import com.itda.apiserver.exception.ShippingInfoNotFoundException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.repository.ShippingInfoCustomRepository;
import com.itda.apiserver.repository.ShippingInfoRepository;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShippingInfoService {

    private final ShippingInfoRepository shippingInfoRepository;
    private final ShippingInfoCustomRepository shippingInfoCustomRepository;
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

    public ShowShippingInfosDto getShowShippingInfoDto(Long userId) {

        ShippingAddressDto defaultShippingAddressDto = getDefaultShippingInfo(userId);
        List<ShippingAddressDto> shippingAddressDtos = getShippingInfoDtos(userId);

        return new ShowShippingInfosDto(defaultShippingAddressDto, shippingAddressDtos);
    }

    private ShippingAddressDto getDefaultShippingInfo(Long userId) {
        if (isDefaultShippingInfoExist(userId)) {
            ShippingInfo defaultShippingInfo = shippingInfoRepository.findByUserIdAndDefaultYNTrue(userId)
                    .orElseThrow(ShippingInfoNotFoundException::new);

            return toShippingAddressDto(defaultShippingInfo);
        }
        return null;
    }

    private List<ShippingAddressDto> getShippingInfoDtos(Long userId) {
        return shippingInfoCustomRepository.findRecentById(userId).stream()
                .distinct()
                .map(this::toShippingAddressDto)
                .collect(Collectors.toList());
    }

    private ShippingAddressDto toShippingAddressDto(ShippingInfo shippingInfo) {
        return ShippingAddressDto.builder()
                .id(shippingInfo.getId())
                .consignee(shippingInfo.getAssignee())
                .phone(shippingInfo.getAssigneePhone())
                .regionOndDepthName(shippingInfo.getAddress().getRegionOneDepthName())
                .regionTwoDepthName(shippingInfo.getAddress().getRegionTwoDepthName())
                .regionThreeDepthName(shippingInfo.getAddress().getRegionThreeDepthName())
                .mainBuildingNo(shippingInfo.getAddress().getMainBuildingNo())
                .subBuildingNo(shippingInfo.getAddress().getSubBuildingNo())
                .zoneNo(shippingInfo.getAddress().getZoneNo())
                .defaultAddrYn(shippingInfo.getDefaultYN())
                .message(shippingInfo.getMessage())
                .build();
    }
}
