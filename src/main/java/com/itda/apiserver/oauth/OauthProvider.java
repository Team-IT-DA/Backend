package com.itda.apiserver.oauth;

public interface OauthProvider {

    UserInfo getUserInfo(String accessToken);

    AccessToken getAccessToken(String code);
}
