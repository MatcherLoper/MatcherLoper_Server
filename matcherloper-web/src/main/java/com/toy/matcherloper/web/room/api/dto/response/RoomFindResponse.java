package com.toy.matcherloper.web.room.api.dto.response;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.user.participant.api.dto.ParticipantWithoutPasswordDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoomFindResponse {

    private Long roomId;
    private String name;
    private RoomStatus status;
    private String possibleOfflineArea;
    private int requiredUserNumber;
    private Long createUserId;
    private Set<ParticipantWithoutPasswordDto> users;
    private List<RoomPositionDto> roomPositions;

    public RoomFindResponse(Room room) {
        this.roomId = room.getId();
        this.name = room.getName();
        this.status = room.getStatus();
        this.possibleOfflineArea = room.getPossibleOfflineArea();
        this.requiredUserNumber = room.getRequiredUserNumber();
        this.createUserId = room.getCreateUserId();
        this.users = toUserDtos(room.getUserRooms());
        this.roomPositions = toRoomPositionList(room.getRequiredPositionList());
    }

    private Set<ParticipantWithoutPasswordDto> toUserDtos(Set<UserRoom> userRooms) {
        return userRooms.stream()
                .map(ur -> new ParticipantWithoutPasswordDto(ur.getUser()))
                .collect(Collectors.toSet());
    }

    private List<RoomPositionDto> toRoomPositionList(List<RoomPosition> requiredPositionList) {
        return requiredPositionList.stream()
                .map(RoomPositionDto::new)
                .collect(Collectors.toList());
    }
}
