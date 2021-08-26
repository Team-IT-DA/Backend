package com.itda.apiserver.oauth.naver;

import com.itda.apiserver.oauth.UserInfo;

public interface NaverLoginService {

    UserInfo getUserInfoByCode(String code);
}
