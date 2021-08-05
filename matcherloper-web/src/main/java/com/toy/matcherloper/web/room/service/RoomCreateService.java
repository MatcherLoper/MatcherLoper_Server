package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Owner;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.exception.NotCreateRoomException;
import com.toy.matcherloper.web.user.owner.service.OwnerFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomCreateService {

    private final RoomRepository roomRepository;
    private final OwnerFindService ownerFindService;

    public Long create(CreateRoomRequest request) {
        Owner owner = ownerFindService.findById(request.getOwnerId());
        checkOwnerHaveAnotherOpenRoom(owner);
        final Room room = Room.create(owner, toPositionList(request.getRoomPositionList()), request.getName(),
                request.getPossibleOfflineArea(), request.getRequiredUserNumber(), RoomStatus.OPEN);
        roomRepository.save(room);
        return room.getId();
    }

    private void checkOwnerHaveAnotherOpenRoom(Owner owner) {
        final Optional<Room> room = roomRepository.findOneByOwner(owner);
        if (room.isPresent() && room.get().isOpen()) {
            throw new NotCreateRoomException("owner already have another open room");
        }
    }

    private List<RoomPosition> toPositionList(List<RoomPositionDto> roomPositionList) {
        return roomPositionList.stream()
                .map(dto -> new RoomPosition(dto.getPosition(), dto.isExist()))
                .collect(Collectors.toList());
    }
}
