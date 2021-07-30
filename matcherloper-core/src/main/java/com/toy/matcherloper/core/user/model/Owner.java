package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.room.model.Room;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue(value = "O")
public class Owner extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
