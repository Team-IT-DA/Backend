package com.itda.apiserver.oauth;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.oauth.kakao.KakaoLoginService;
import com.itda.apiserver.oauth.naver.NaverLoginService;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final NaverLoginService naverLoginService;
    private final KakaoLoginService kakaoLoginService;

    public TokenResponseDto login(String code, SocialResourceServer socialServer) {

        OauthProvider oauthProvider = getOauthProvider(socialServer);
        UserInfo userInfo = oauthProvider.requestUserInfo(code);
        String email = userInfo.getEmail();

        if (isNewUser(email)) {
            signUp(userInfo);
        }

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = tokenProvider.createToken(user.getId());

        return new TokenResponseDto(token);
    }

    private OauthProvider getOauthProvider(SocialResourceServer socialServer) {
        return socialServer == SocialResourceServer.NAVER ? naverLoginService : kakaoLoginService;
    }

    private boolean isNewUser(String email) {
        int count = userRepository.countByEmail(email);
        return count == 0;
    }

    private User signUp(UserInfo userInfo) {
        return userRepository.save(userInfo.toUser());
    }
}