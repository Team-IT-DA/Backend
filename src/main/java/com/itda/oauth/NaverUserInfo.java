package com.itda.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
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
