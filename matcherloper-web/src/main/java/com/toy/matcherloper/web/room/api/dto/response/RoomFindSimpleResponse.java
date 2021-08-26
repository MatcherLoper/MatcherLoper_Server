package com.toy.matcherloper.web.room.api.dto.response;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomFindSimpleResponse {

    private Long roomId;
    private String myPosition;
    private String title;
    private String roomStatus;

    public RoomFindSimpleResponse(Room room, PositionType positionType) {
        this.roomId = room.getId();
        this.myPosition = positionType.name();
        this.title = room.getName();
        this.roomStatus = room.getStatus().getDetail();
    }
}
