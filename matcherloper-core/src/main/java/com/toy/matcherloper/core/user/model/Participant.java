package com.toy.matcherloper.core.user.model;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue(value = "P")
public class Participant extends User {
}
