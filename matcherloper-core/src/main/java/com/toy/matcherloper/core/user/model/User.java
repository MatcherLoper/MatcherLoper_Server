package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.user.exception.NotMatchedPasswordException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "introduction")
    private String introduction;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserPosition> userPositionSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Skill> skillSet = new HashSet<>();

    @Embedded
    private Address address;

    @Builder
    public User(Long id, String email, String password, String name, String phoneNumber, String introduction,
                Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.userPositionSet = userPositionSet;
        this.skillSet = skillSet;
        this.address = address;
    }

    public void addUserPosition(UserPosition position) {
        this.userPositionSet.add(position);
        position.changeUser(this);
    }

    public void addSkill(Skill skill) {
        this.skillSet.add(skill);
        skill.changeUser(this);
    }

    public void update(String email, String password, String name, String phoneNumber, String introduction,
                       Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;

        for (UserPosition userPosition : userPositionSet) {
            addUserPosition(userPosition);
        }

        for (Skill skill : skillSet) {
            addSkill(skill);
        }

        this.address = address;
    }

    public void checkMatchedPassword(String requestPassword) {
        if (!requestPassword.equals(password)) {
            throw new NotMatchedPasswordException("Password is not matched!");
        }
    }
}
