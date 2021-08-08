package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "introduction")
    private String introduction;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @OneToMany(mappedBy = "user", fetch = EAGER)
    private Set<UserPosition> userPositionSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = EAGER)
    private Set<Skill> skillSet = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private Address address;

    @Builder
    public User(String email, String password, String name, String phoneNumber, String introduction, RoleType roleType,
                Set<UserPosition> userPositionSet, Set<Skill> skillSet, Room room, Address address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.roleType = roleType;
        this.userPositionSet = userPositionSet;
        this.skillSet = skillSet;
        this.room = room;
        this.address = address;
    }

    public static User create(String email, String password, String name, String phoneNumber, String introduction,
                              Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        return new User(email, password, name, phoneNumber, introduction, RoleType.NONE, userPositionSet, skillSet, address);
    }

    public User(String email, String password, String name, String phoneNumber, String introduction, RoleType roleType,
                Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        for (UserPosition userPosition : userPositionSet) addUserPosition(userPosition);
        for (Skill skill : skillSet) addSkill(skill);
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.roleType = roleType;
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
        for (UserPosition userPosition : userPositionSet) addUserPosition(userPosition);
        for (Skill skill : skillSet) addSkill(skill);
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.address = address;
    }

    public void createRoom(List<RoomPosition> roomPositionList, String name, int requiredUserNumber, String possibleOfflineArea) {
        this.roleType = RoleType.OWNER;
        this.room = Room.create(this, this.getId(), roomPositionList, name, possibleOfflineArea, requiredUserNumber);
    }

    public boolean isNotMatchingPassword(String password) {
        return !this.password.equals(password);
    }
}
