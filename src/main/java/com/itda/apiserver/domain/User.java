package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Core {

    private String name;
    private String phone;
    private String email;

    @Column(columnDefinition = "ENUM('SELLER', 'USER', 'ADMIN')")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String password;
    private String account;

    @OneToMany(mappedBy = "user")
    private final List<ShippingInfo> shippingInfos = new ArrayList<>();

    @Builder
    public User(String name, String phone, String email, Role role, String password, String account) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.password = password;
        this.account = account;
    }

}
