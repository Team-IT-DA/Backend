package com.itda.apiserver.oauth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itda.apiserver.oauth.AccessToken;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverAccessToken implements AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @Override
    public String value() {
        return accessToken;
    }
}
