package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShowShippingInfosDto {

    private ShippingAddressDto defaultShippingAddress;
    private List<ShippingAddressDto> shippingAddress;
}
