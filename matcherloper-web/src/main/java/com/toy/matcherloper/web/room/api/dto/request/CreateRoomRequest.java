package com.toy.matcherloper.web.room.api.dto.request;

import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRoomRequest {

    @NotNull(message = "사용자 아이디가 필요합니다.")
    private Long userId;

    @NotNull(message = "프로젝트에 필요한 포지션을 입력해 주세요.")
    private List<RoomPositionDto> roomPositionList;

    @NotBlank(message = "방 이름이 비어있으면 안됩니다.")
    private String name;

    @NotNull(message = "오프라인 모임이 가능한 지역은 널이면 안됩니다.")
    private String possibleOfflineArea;

    public CreateRoomRequest(Long userId, List<RoomPositionDto> roomPositionList, String name, String possibleOfflineArea) {
        this.userId = userId;
        this.roomPositionList = roomPositionList;
        this.name = name;
        this.possibleOfflineArea = possibleOfflineArea;
    }
}
