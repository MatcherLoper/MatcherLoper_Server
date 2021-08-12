package com.toy.matcherloper.core.room.model;

import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_position_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(value = STRING)
    private PositionType position;

    @Column(name = "count")
    private int count;

    @Builder
    public RoomPosition(Room room, PositionType position, int count) {
        this.room = room;
        this.position = position;
        this.count = count;
    }

    public RoomPosition(PositionType position, int count) {
        this.position = position;
        this.count = count;
    }
}
