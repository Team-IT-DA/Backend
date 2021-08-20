package com.itda.apiserver.oauth;

public interface OauthProvider {

    SocialProfile getUserInfo(String accessToken);

    AccessToken getAccessToken(String code);
}
