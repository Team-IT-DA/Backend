package com.itda.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverPersonalInfo {

    private String id;
    private String name;
    private String email;
    private String mobile;

    @JsonProperty("mobile_e164")
    private String globalMobile;

}
