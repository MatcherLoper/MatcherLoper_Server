package com.toy.matcherloper.web.user.api.dto;

import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.UserPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserPositionDto {

    @NotEmpty(message = "포지션 타입을 명시해주세요")
    private PositionType type;

    public UserPositionDto(PositionType type) {
        this.type = type;
    }

    public UserPositionDto(UserPosition position) {
        this.type = position.getType();
    }
}
