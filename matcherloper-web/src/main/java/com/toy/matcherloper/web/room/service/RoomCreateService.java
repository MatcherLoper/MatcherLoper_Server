package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Owner;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.exception.NotCreateRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomCreateService {

    private final RoomRepository roomRepository;
    private final OwnerFindService ownerFindService;

    public Long create(CreateRoomRequest request) {
        Owner owner = ownerFindService.findById(request.getOwnerId());
        checkOwnerHaveAnotherOpenRoom(owner);
        final Room room = Room.create(owner, request.getRoomPositionList(), request.getName(),
                request.getPossibleOfflineArea(), request.getRequiredUserNumber());
        roomRepository.save(room);
        return room.getId();
    }

    private void checkOwnerHaveAnotherOpenRoom(Owner owner) {
        final Optional<Room> room = roomRepository.findByOwner(owner);
        if (room.isPresent() && room.get().isOpen()) {
            throw new NotCreateRoomException("owner already  have another open room");
        }
    }
}
