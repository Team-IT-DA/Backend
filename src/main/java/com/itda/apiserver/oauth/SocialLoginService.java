package com.itda.apiserver.oauth;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final Map<String, OauthProvider> oauthProviderMap;


    public TokenResponseDto naverLogin(String code) {
        return login(code, oauthProviderMap.get("naverLoginService"));
    }

    public TokenResponseDto kakaoLogin(String code) {
        return login(code, oauthProviderMap.get("kakaoLoginService"));
    }

    private TokenResponseDto login(String code, OauthProvider oauthProvider) {

        UserInfo userInfo = oauthProvider.requestUserInfo(code);
        String email = userInfo.getEmail();

        if (isNewUser(email)) {
            signUp(userInfo);
        }

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = tokenProvider.createToken(user.getId());

        return new TokenResponseDto(token, user.getName(), user.isSeller());
    }

    private boolean isNewUser(String email) {
        return !userRepository.existsByEmail(email);
    }

    private User signUp(UserInfo userInfo) {
        return userRepository.save(userInfo.toUser());
    }
}
