package com.toy.matcherloper.core.room.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.EnumType.STRING;

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

    @OneToMany(mappedBy = "room")
    private Set<UserRoom> userRooms = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPosition> requiredPositionList = new ArrayList<>();

    @Builder
    public Room(String name, RoomStatus status, String possibleOfflineArea, Long createUserId,
                int requiredUserNumber, List<RoomPosition> requiredPositionList) {
        this.name = name;
        this.status = status;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = requiredUserNumber;
        this.requiredUserNumber = calculateRequiredUserNumber(requiredPositionList);
        this.createUserId = createUserId;
        this.requiredPositionList = requiredPositionList;
    }

    public Room(Long createUserId, List<RoomPosition> roomPositions, String name, String possibleOfflineArea) {
        addRoomPositions(roomPositions);
        this.createUserId = createUserId;
        this.name = name;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = calculateRequiredUserNumber(requiredPositionList);
        this.status = RoomStatus.OPEN;
    }

    public static Room create(Long createUserId, List<RoomPosition> roomPositions, String name, String possibleOfflineArea) {
        return new Room(createUserId, roomPositions, name, possibleOfflineArea);
    }

    public void addUserRoom(UserRoom userRoom) {
        this.userRooms.add(userRoom);
    }

    private int calculateRequiredUserNumber(List<RoomPosition> requiredPositionList) {
        return requiredPositionList.stream()
                .mapToInt(RoomPosition::getCount)
                .sum();
    }

    private void addRoomPositions(List<RoomPosition> roomPositions) {
        for (RoomPosition roomPosition : roomPositions) {
            this.requiredPositionList.add(roomPosition);
            roomPosition.changeRoom(this);
        }
    }

    public boolean isOpen() {
        return this.status == RoomStatus.OPEN;
    }

    public void start() {
        this.status = RoomStatus.START;
    }

    public void close() {
        this.status = RoomStatus.CLOSED;
        for (UserRoom userRoom : this.userRooms) {
            userRoom.close();
        }
    }

    public void delete() {
        for (UserRoom userRoom : this.userRooms) {
            userRoom.deleteRoom();
        }
    }

    public boolean canJoin(PositionType position) {
        return this.requiredPositionList.stream()
                .anyMatch(roomPosition -> roomPosition.canJoin(position));
    }

    public void joinUser(User user, PositionType position) {
        final RoomPosition roomPosition = this.requiredPositionList.stream()
                .filter(rp -> rp.canJoin(position))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("not found room position"));
        roomPosition.reduceCount();
        reduceRequiredNumber();
        user.join();
    }

    private void reduceRequiredNumber() {
        this.requiredUserNumber--;
        if (this.requiredUserNumber == 0) {
            this.status = RoomStatus.FULL;
        }
    }
}
