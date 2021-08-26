package com.itda.apiserver.oauth.naver;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLoginServiceRestTemplate implements OauthProvider, NaverLoginService {

    private final String GRANT_TYPE = "authorization_code";
    private final String ACCESS_TOKEN_URI = "https://nid.naver.com/oauth2.0/token";
    private final String USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";

    @Value("${oauth.naver.client_id}")
    private String clientId;

    @Value("${oauth.naver.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public UserInfo getUserInfoByCode(String code) {

        AccessToken accessToken = getAccessToken(code);

        if (accessToken.value() == null) {
            throw new InvalidTokenException(accessToken.getErrorDescription());
        }

        return getUserInfo(accessToken.value());
    }

    @Override
    public AccessToken getAccessToken(String code) {

        URI uri = UriComponentsBuilder.fromHttpUrl(ACCESS_TOKEN_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .build()
                .toUri();

        NaverAccessToken naverAccessToken = restTemplate.getForObject(uri, NaverAccessToken.class);
        log.info("access token: {}", naverAccessToken);
        return naverAccessToken;
    }

    @Override
    public UserInfo getUserInfo(String accessToken) {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);

        return restTemplate.postForObject(USER_INFO_URI, new HttpEntity<>(header), NaverUserInfo.class);
    }
}
