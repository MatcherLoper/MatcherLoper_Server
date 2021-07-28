package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.room.model.Room;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue(value = "P")
public class Participant extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
