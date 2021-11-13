package com.itda.apiserver.dto;

import com.itda.apiserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SellerProfileDto {

    private String name;
    private String telephone;
    private String email;
    private String password;

    public static SellerProfileDto from(User user) {
        return new SellerProfileDto(user.getName(), user.getPhone(), user.getEmail(), user.getPassword());
    }

}
