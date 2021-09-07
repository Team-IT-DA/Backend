package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShippingInfo extends Core {

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String assignee;
    private String message;
    private String assigneePhone;

    @Column(name = "default_yn")
    private Boolean defaultYN;

    public void beNotDefault() {
        this.defaultYN = false;
    }
}
