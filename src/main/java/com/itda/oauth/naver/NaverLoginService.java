package com.itda.oauth.naver;

import com.itda.oauth.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NaverLoginService implements OauthProvider {

    private final String GRANT_TYPE = "authorization_code";

    @Value("{oauth.naver.client_id}")
    private String clientId;

    @Value("{oauth.naver.secret}")
    private String clientSecret;

    private final WebClient webClient;

    @Override
    public void login(String code) {
    }

    public NaverAccessToken getAccessToken(String code) {

        return webClient.mutate()
                .build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder.scheme("https")
                                .host("nid.naver.com")
                                .path("/oauth2.0/token")
                                .queryParam("grant_type", GRANT_TYPE)
                                .queryParam("client_id", clientId)
                                .queryParam("client_secret", clientSecret)
                                .queryParam("code", code)
                                .build())
                .retrieve()
                .bodyToMono(NaverAccessToken.class)
                .block();
    }

    public NaverUserInfo getUserInfo(String accessToken) {

        return webClient.mutate()
                .build()
                .get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(NaverUserInfo.class)
                .block();
    }
}
