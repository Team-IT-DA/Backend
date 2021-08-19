package com.itda.oauth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itda.oauth.naver.NaverPersonalInfo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverUserInfo {

    @JsonProperty("resultcode")
    private String resultCode;

    private String message;
    private NaverPersonalInfo response;

}
