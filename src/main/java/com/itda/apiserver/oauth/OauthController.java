package com.itda.apiserver.oauth;

import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class OauthController {

    private final SocialLoginService socialLoginService;

    /**
     * @param code 클라이언트단에서 리소스서버에서 전달 받고 전송하는 authorization code
     */
    @GetMapping("/naver")
    public ApiResult<TokenResponseDto> naverLogin(@RequestParam String code) {
        TokenResponseDto response = socialLoginService.login(code);
        return ApiResult.ok(response);
    }
}
