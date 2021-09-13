package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
    private String name;
    private String telephone;
    private String email;
    private String password;
}
