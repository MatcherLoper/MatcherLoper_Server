package com.toy.matcherloper.core.room.model;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_room_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "position")
    private PositionType position;

    public UserRoom(User user, Room room, PositionType position) {
        this.user = user;
        this.room = room;
        this.position = position;
        room.addUserRoom(this);
    }

    public void close() {
        this.user.finishProject();
    }

    public void deleteRoom() {
        this.room = null;
        close();
    }

    public void leaveUser() {
        this.room.leaveUser(this, user, this.position);
        this.user = null;
    }
}
