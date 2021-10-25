package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.ShippingInfoDto;
import com.itda.apiserver.dto.ShippingInfoResponse;
import com.itda.apiserver.dto.ShowShippingInfosDto;
import com.itda.apiserver.service.ShippingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shippingInfos")
public class ShippingInfoController {

    private final ShippingInfoService shippingInfoService;

    @LoginRequired
    @PostMapping
    public ApiResult<ShippingInfoResponse> addShippingInfo(@UserId Long userId, @RequestBody ShippingInfoDto shippingInfoDto) {
        Long shippingInfoId = shippingInfoService.addShippingInfo(userId, shippingInfoDto);
        return ApiResult.ok(new ShippingInfoResponse(shippingInfoId));
    }

    @LoginRequired
    @GetMapping
    public ApiResult<ShowShippingInfosDto> showShippingInfos(@UserId Long userId) {
        ShowShippingInfosDto showShippingInfoDto = shippingInfoService.getShowShippingInfoDto(userId);
        return ApiResult.ok(showShippingInfoDto);
    }
}
