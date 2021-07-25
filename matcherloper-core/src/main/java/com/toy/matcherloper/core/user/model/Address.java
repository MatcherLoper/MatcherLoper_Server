package com.toy.matcherloper.core.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {

    private String city;
    private String detailed;

    //constructor
    public Address(String city, String detailed) {
        this.city = city;
        this.detailed = detailed;
    }
}