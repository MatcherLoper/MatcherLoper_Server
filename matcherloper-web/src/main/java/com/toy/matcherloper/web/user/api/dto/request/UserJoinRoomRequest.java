package com.toy.matcherloper.web.user.api.dto.request;

import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRoomRequest {

    private PositionType position;

    public UserJoinRoomRequest(PositionType position) {
        this.position = position;
    }
}
