package com.toy.matcherloper.core.user.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {

    private String city;
    private String detailed;

    public Address(String city, String detailed) {
        this.city = city;
        this.detailed = detailed;
    }
}