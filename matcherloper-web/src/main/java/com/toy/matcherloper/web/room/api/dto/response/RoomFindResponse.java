package com.toy.matcherloper.web.room.api.dto.response;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.user.model.Participant;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.user.owner.api.dto.OwnerWithoutPasswordDto;
import com.toy.matcherloper.web.user.participant.api.dto.ParticipantWithoutPasswordDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoomFindResponse {

    private Long roomId;
    private String name;
    private RoomStatus status;
    private String possibleOfflineArea;
    private int requiredUserNumber;
    private OwnerWithoutPasswordDto owner;
    private List<ParticipantWithoutPasswordDto> participantList;
    private List<RoomPositionDto> roomPositionList;

    public RoomFindResponse(Room room) {
        this.roomId = room.getId();
        this.name = room.getName();
        this.status = room.getStatus();
        this.possibleOfflineArea = room.getPossibleOfflineArea();
        this.requiredUserNumber = room.getRequiredUserNumber();
        this.owner = new OwnerWithoutPasswordDto(room.getOwner());
        this.participantList = toParticipantList(room.getParticipantList());
        this.roomPositionList = toRoomPositionList(room.getRequiredPositionList());
    }

    private List<ParticipantWithoutPasswordDto> toParticipantList(List<Participant> participantList) {
        return participantList.stream()
                .map(ParticipantWithoutPasswordDto::new)
                .collect(Collectors.toList());
    }

    private List<RoomPositionDto> toRoomPositionList(List<RoomPosition> requiredPositionList) {
        return requiredPositionList.stream()
                .map(RoomPositionDto::new)
                .collect(Collectors.toList());
    }
}
