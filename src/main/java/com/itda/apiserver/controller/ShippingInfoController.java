package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.dto.ShippingInfoResponse;
import com.itda.apiserver.service.ShippingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShippingInfoController {

    private final ShippingInfoService shippingInfoService;

    @LoginRequired
    @PostMapping("/api/shippingInfos")
    public ApiResult<ShippingInfoResponse> addShippingInfo(@UserId Long userId, @RequestBody ShippingInfoDto shippingInfoDto) {
        Long shippingInfoId = shippingInfoService.addShippingInfo(userId, shippingInfoDto);
        return ApiResult.ok(new ShippingInfoResponse(shippingInfoId));
    }
}
