package com.itda.apiserver.oauth.kakao;

import com.itda.apiserver.exception.InvalidTokenException;
import com.itda.apiserver.oauth.AccessToken;
import com.itda.apiserver.oauth.OauthProvider;
import com.itda.apiserver.oauth.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService implements OauthProvider {

    private final String GRANT_TYPE = "authorization_code";
    private final String REDIRECT_URI = "http://localhost:3000/kakao/callback";
    private final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private final String USER_INFO_URI = "https://kapi.kakao.com//v2/user/me";

    @Value("${oauth.kakao.client_id}")
    private String clientId;

    @Value("${oauth.kakao.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public UserInfo requestUserInfo(String code) {

        String accessToken = getAccessToken(code).value();

        if (accessToken == null) {
            throw new InvalidTokenException();
        }

        return getUserInfo(accessToken);
    }

    private AccessToken getAccessToken(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);


        AccessToken accessToken = restTemplate.postForObject(ACCESS_TOKEN_URI, params, KakaoAccessTokenResponse.class);
        log.info("access token: {}", accessToken.toString());
        return accessToken;
    }

    private UserInfo getUserInfo(String accessToken) {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);

        return restTemplate.postForObject(USER_INFO_URI, new HttpEntity<>(header), KakaoUserInfo.class);
    }
}
