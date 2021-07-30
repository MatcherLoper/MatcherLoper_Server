package com.toy.matcherloper.core.room.model;

import com.toy.matcherloper.core.common.entity.BaseEntity;
import com.toy.matcherloper.core.user.model.Owner;
import com.toy.matcherloper.core.user.model.Participant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "room")
    private List<Participant> participantList = new ArrayList<>();

    @OneToOne(fetch = LAZY, mappedBy = "room")
    private Owner owner;

    @OneToMany(mappedBy = "room", fetch = LAZY)
    private List<RoomPosition> requiredPositionList = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Enumerated(value = STRING)
    @Column(name = "status")
    private RoomStatus status;

    @Column(name = "possible_offline_area")
    private String possibleOfflineArea;

    @Column(name = "required_user_number")
    private int requiredUserNumber;

    @Builder
    public Room(List<Participant> participantList, Owner owner, List<RoomPosition> requiredPositionList, String name,
                RoomStatus status, String possibleOfflineArea, int requiredUserNumber) {
        this.participantList = participantList;
        this.owner = owner;
        this.requiredPositionList = requiredPositionList;
        this.name = name;
        this.status = status;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = requiredUserNumber;
    }

    public Room(Owner owner, List<RoomPosition> requiredPositionList, String name,
                String possibleOfflineArea, int requiredUserNumber) {
        this.owner = owner;
        this.requiredPositionList = requiredPositionList;
        this.name = name;
        this.possibleOfflineArea = possibleOfflineArea;
        this.requiredUserNumber = requiredUserNumber;
    }

    public static Room create(Owner owner, List<RoomPosition> positionList, String name,
                              String possibleOfflineArea, int requiredUserNumber) {
        return new Room(owner, positionList, name, possibleOfflineArea, requiredUserNumber);
    }

    public boolean isOpen() {
        return this.status == RoomStatus.OPEN;
    }
}
