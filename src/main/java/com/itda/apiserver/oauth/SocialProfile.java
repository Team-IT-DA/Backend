package com.itda.apiserver.oauth;

import com.itda.apiserver.domain.User;

public interface SocialProfile {

    String getEmail();

    User toUser();
}
