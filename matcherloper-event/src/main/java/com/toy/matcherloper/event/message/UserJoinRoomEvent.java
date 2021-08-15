package com.toy.matcherloper.event.message;

import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.Getter;

@Getter
public class UserJoinRoomEvent extends Event {

    private final Long userId;
    private final PositionType position;

    public UserJoinRoomEvent(Long userId, PositionType position) {
        this.userId = userId;
        this.position = position;
    }
}
