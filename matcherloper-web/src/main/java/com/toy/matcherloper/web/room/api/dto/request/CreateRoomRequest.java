package com.toy.matcherloper.web.room.api.dto.request;

import com.toy.matcherloper.core.room.model.RoomPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRoomRequest {

    @NotNull(message = "방장 아이디가 필요합니다.")
    private Long ownerId;

    private List<RoomPosition> roomPositionList;

    @NotBlank(message = "방 이름이 비어있으면 안됩니다.")
    private String name;

    @NotNull(message = "오프라인 모임이 가능한 지역은 널이면 안됩니다.")
    private String possibleOfflineArea;

    @NotNull(message = "필요한 유저 수를 입력해 주세요.")
    private int requiredUserNumber;
}