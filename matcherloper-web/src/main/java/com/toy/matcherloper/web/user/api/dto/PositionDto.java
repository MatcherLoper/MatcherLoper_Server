package com.toy.matcherloper.web.user.api.dto;

import com.toy.matcherloper.core.user.model.PositionType;
import com.toy.matcherloper.core.user.model.UserPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PositionDto {

    private PositionType type;

    public PositionDto(PositionType type) {
        this.type = type;
    }

    public PositionDto(UserPosition position) {
        this.type = position.getType();
    }
}
