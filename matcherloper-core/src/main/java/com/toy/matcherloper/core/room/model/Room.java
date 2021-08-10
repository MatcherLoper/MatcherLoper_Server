package com.toy.matcherloper.core.room.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(value = STRING)
    @Column(name = "status")
    private RoomStatus status;

    @Column(name = "possible_offline_area")
    private String possibleOfflineArea;

    @Column(name = "required_user_number")
    private int requiredUserNumber;

    @Column(name = "create_user_id")
    private Long createUserId;

    @OneToMany(mappedBy = "room", fetch = LAZY)
    private Set<User> userSet = new HashSet<>();

    @OneToMany(mappedBy = "room", fetch = LAZY)
    private List<RoomPosition> requiredPositionList = new ArrayList<>();

    @Builder
    public Room(String name, RoomStatus status, String possibleOfflineArea, int requiredUserNumber, Long createUserId,
                Set<User> userSet, List<RoomPosition> requiredPositionList) {
        this.name = name;
        this.status = status;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = requiredUserNumber;
        this.createUserId = createUserId;
        this.userSet = userSet;
        this.requiredPositionList = requiredPositionList;
    }

    public Room(User user, List<RoomPosition> positionList, String name, String possibleOfflineArea, int requiredUserNumber) {
        this.userSet.add(user);
        this.requiredPositionList = positionList;
        this.name = name;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = requiredUserNumber;
        this.status = RoomStatus.OPEN;
    }

    public static Room create(User user, List<RoomPosition> toPositionList, String name,
                              String possibleOfflineArea, int requiredUserNumber) {
        return new Room(user, toPositionList, name, possibleOfflineArea, requiredUserNumber);
    }

    public boolean isOpen() {
        return this.status == RoomStatus.OPEN;
    }

    public void start() {
        this.status = RoomStatus.CLOSED;
    }
}
