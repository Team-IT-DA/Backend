package com.itda.apiserver.oauth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.oauth.SocialProfile;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverUserInfo implements SocialProfile {

    @JsonProperty("resultcode")
    private String resultCode;

    private String message;
    private Response response;

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public User toUser() {
        return new User(response.name, response.mobile,
                response.email, null, null);
    }


    @Getter
    @ToString
    public class Response {

        private String id;
        private String name;
        private String email;
        private String mobile;
    }
}
