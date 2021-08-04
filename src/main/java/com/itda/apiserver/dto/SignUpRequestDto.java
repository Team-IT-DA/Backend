package com.itda.apiserver.dto;

import com.itda.apiserver.domain.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDto {

    private String name;
    private String telephone;
    private String email;
    private String password;
    private String authCode;

}
