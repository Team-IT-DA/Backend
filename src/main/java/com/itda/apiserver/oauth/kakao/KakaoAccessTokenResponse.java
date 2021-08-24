package com.itda.apiserver.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itda.apiserver.oauth.AccessToken;
import lombok.ToString;

@ToString
public class KakaoAccessTokenResponse implements AccessToken {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;

    @Override
    public String value() {
        return accessToken;
    }

}
