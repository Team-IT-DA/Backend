package com.itda.apiserver.oauth.naver;

import com.itda.apiserver.oauth.AccessToken;
import com.itda.apiserver.oauth.OauthProvider;
import com.itda.apiserver.oauth.UserInfo;

public class NaverLoginServiceRestTemplate implements OauthProvider, NaverLoginService {

    @Override
    public UserInfo getUserInfo(String accessToken) {
        return null;
    }

    @Override
    public AccessToken getAccessToken(String code) {
        return null;
    }

    @Override
    public UserInfo getUserInfoByCode(String code) {
        return null;
    }
}
