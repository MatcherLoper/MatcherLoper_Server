package com.toy.matcherloper.core.user.model;

import com.sun.istack.NotNull;
import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.event.dispatcher.Events;
import com.toy.matcherloper.event.message.MatchingEvent;
import com.toy.matcherloper.event.message.UnSubscribeEvent;
import com.toy.matcherloper.matching.notification.NotificationTitle;
import com.toy.matcherloper.matching.type.TopicType;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.toy.matcherloper.core.user.model.type.RoleType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "device_token")
    private String deviceToken;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPosition> userPositionSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Skill> skillSet = new HashSet<>();

    @Embedded
    private Address address;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private AuthProviderType authProvider;

    @Builder
    public User(String email, String password, String name, String phoneNumber, String introduction, RoleType roleType,
                Set<UserPosition> userPositionSet, Set<Skill> skillSet, Room room, Address address,
                AuthProviderType provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.roleType = roleType;
        this.userPositionSet = userPositionSet;
        this.skillSet = skillSet;
        this.address = address;
        this.authProvider = provider;
    }

    public static User create(String email, String password, String name, String phoneNumber, String introduction,
                              Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address,
                              AuthProviderType authProvider) {
        return new User(email, password, name, phoneNumber, introduction, RoleType.NONE,
                userPositionSet, skillSet, address, authProvider);
    }

    public User(String email, String password, String name, String phoneNumber, String introduction, RoleType roleType,
                Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address, AuthProviderType authProvider) {
        for (UserPosition userPosition : userPositionSet) addUserPosition(userPosition);
        for (Skill skill : skillSet) addSkill(skill);
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.roleType = roleType;
        this.address = address;
        this.authProvider = authProvider;
    }

    public static User createFromOAuth2(String email, String name, String deviceToken,
                                        AuthProviderType authProviderType, RoleType roleType) {
        return new User(email, name, deviceToken, authProviderType, roleType);
    }

    public User(String email, String name, String deviceToken, AuthProviderType authProviderType, RoleType roleType) {
        this.email = email;
        this.name = name;
        this.deviceToken = deviceToken;
        this.authProvider = authProviderType;
        this.roleType = roleType;
    }

    public void addUserPosition(UserPosition position) {
        this.userPositionSet.add(position);
        position.changeUser(this);
    }

    public void addSkill(Skill skill) {
        this.skillSet.add(skill);
        skill.changeUser(this);
    }

    public void update(String password, String name, String phoneNumber, String introduction,
                       Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        updateSkills(skillSet);
        updatePositions(userPositionSet);
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.address = address;
    }

    private void updateSkills(Set<Skill> skillSet) {
        clearSkills();
        skillSet.forEach(this::addSkill);
    }

    private void updatePositions(Set<UserPosition> userPositionSet) {
        clearUserPositions();
        userPositionSet.forEach(this::addUserPosition);
    }

    private void clearSkills() {
        for (Skill skill : this.skillSet) {
            skill.changeUser(null);
        }
        this.skillSet.clear();
    }

    private void clearUserPositions() {
        for (UserPosition position : this.userPositionSet) {
            position.changeUser(null);
        }
        this.userPositionSet.clear();
    }

    public Room createRoom(List<RoomPosition> roomPositionList, String name, String possibleOfflineArea) {
        this.roleType = OWNER;
        return Room.create(this.getId(),
                roomPositionList,
                name,
                possibleOfflineArea);
    }

    public boolean isNotMatchingPassword(String password) {
        return !this.password.equals(password);
    }

    public void finishProject() {
        this.roleType = NONE;
    }

    public void join() {
        Events.raise(new UnSubscribeEvent(this.deviceToken, TopicType.MATCHING.getToken()));
        this.roleType = PARTICIPANT;
    }

    public void leaveRoom() {
        Events.raise(new MatchingEvent(TopicType.MATCHING.getToken(), NotificationTitle.ROOM_STATE_CHANGE_DETECTION));
        this.roleType = NONE;
    }

    public boolean canJoin() {
        return this.roleType.equals(NONE);
    }

    public void changeDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void addProfileFromOAuth2(String phoneNumber, String introduction, Set<UserPosition> userPositionSet,
                                     Set<Skill> skillSet, Address address) {
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        userPositionSet.forEach(this::addUserPosition);
        skillSet.forEach(this::addSkill);
        this.address = address;
    }

    public void changeMatchingState(RoleType matching) {
        this.roleType = matching;
    }
}
