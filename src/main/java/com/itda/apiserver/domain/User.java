package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private String sellerDescription;
    private String sellerImageUrl;

    @OneToMany(mappedBy = "user")
    private final List<ShippingInfo> shippingInfos = new ArrayList<>();

    public User(String name, String phone, String email, String password, String authCode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = getRole(authCode);
    }

    @Builder
    public User(String name, String phone, String email, Role role, String password, String account) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.password = password;
        this.account = account;
    }

    public void updateProfile(String email, String password, String name, String telephone) {
        this.email = email.equals("") ? this.email : email;
        this.password = password.equals("") ? this.password : password;
        this.name = name.equals("") ? this.name : name;
        this.phone = telephone.equals("") ? this.phone : telephone;
    }

    private Role getRole(String authCode) {
        return authCode == null ? Role.USER : Role.SELLER;
    }

    public boolean isPasswordMatching(String password) {
        return this.password.equals(password);
    }

    public void updateSellerInfo(String imgUrl, String description) {
        this.sellerImageUrl = imgUrl;
        this.sellerDescription = description;
    }

    public boolean isSeller() {
        return this.role == Role.SELLER;
    }

}
