package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
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

    @OneToMany(mappedBy = "user")
    private List<UserPosition> userPositionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Skill> skillList = new ArrayList<>();

    @Embedded
    private Address address;

    @Builder
    public User(Long id, String email, String password, String name, String phoneNumber, String introduction,
                List<UserPosition> userPositionList, List<Skill> skillList, Address address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.userPositionList = userPositionList;
        this.skillList = skillList;
        this.address = address;
    }

    public void addUserPosition(UserPosition position) {
        this.userPositionList.add(position);
        position.changeUser(this);
    }

    public void addSkill(Skill skill) {
        this.skillList.add(skill);
        skill.changeUser(this);
    }

    public void update(String email, String password, String name, String phoneNumber, String introduction,
                List<UserPosition> userPositionList, List<Skill> skillList, Address address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;

        for (UserPosition userPosition : userPositionList) {
            addUserPosition(userPosition);
        }

        for (Skill skill : skillList) {
            addSkill(skill);
        }

        this.address = address;
    }

    public void checkMatchedPassword(String requestPassword){
        if (!requestPassword.equals(password)) {
            throw new IllegalArgumentException("Password is not matched");
        }
    }
}
