package com.toy.matcherloper.core.room.model;

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
public class Room {

    @Id
    @Column("room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "participant", fetch = LAZY)
    private List<Participant> participantList = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Owner owner;

    @OneToMany(mappedBy = "room_position", fetch = LAZY)
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
}
