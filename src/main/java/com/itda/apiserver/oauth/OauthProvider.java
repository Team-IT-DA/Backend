package com.itda.apiserver.oauth;

public interface OauthProvider {

    UserInfo requestUserInfo(String code);
}
