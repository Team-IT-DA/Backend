package com.itda.apiserver.oauth;

import com.itda.apiserver.domain.User;

public interface UserInfo {

    String getEmail();

    User toUser();
}
