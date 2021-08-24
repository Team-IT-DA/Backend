package com.itda.apiserver.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.oauth.UserInfo;
import lombok.Getter;

@Getter
public class KakaoUserInfo implements UserInfo {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;


    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public User toUser() {
        return new User(kakaoAccount.profile.nickname, null, kakaoAccount.email, id.toString(), null);
    }

    @Getter
    public class KakaoAccount {

        private String email;
        private Profile profile;

        @Getter
        public class Profile {

            private String nickname;
        }
    }
}
