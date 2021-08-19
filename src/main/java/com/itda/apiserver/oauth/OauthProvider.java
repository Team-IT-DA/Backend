package com.itda.apiserver.oauth;

public interface OauthProvider {

    void login();

    String getAccessToken();
}
