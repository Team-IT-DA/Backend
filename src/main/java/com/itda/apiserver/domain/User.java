package com.itda.apiserver.domain;

import lombok.AccessLevel;
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
}
