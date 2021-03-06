package com.toy.matcherloper.web.room.api.dto;

import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomPositionDto {

    private PositionType position;
    private int count;

    public RoomPositionDto(RoomPosition roomPosition) {
        this.position = roomPosition.getPosition();
        this.count = roomPosition.getCount();
    }
}
