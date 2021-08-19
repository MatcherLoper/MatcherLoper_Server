package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.room.repository.RoomPositionRepository;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import com.toy.matcherloper.event.dispatcher.Events;
import com.toy.matcherloper.event.handler.MatchingEventHandler;
import com.toy.matcherloper.matching.process.FcmMatchingSystem;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.exception.RoomNotCreateException;
import com.toy.matcherloper.web.user.service.UserFindService;
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
    private final UserRoomRepository userRoomRepository;
    private final RoomPositionRepository roomPositionRepository;
    private final UserFindService userFindService;
    private final FcmMatchingSystem fcmMatchingSystem;

    public Long create(CreateRoomRequest request) {
        User user = userFindService.findById(request.getUserId());
        checkOwnerHaveAnotherOpenRoom(user);
        Events.handleAsync(new MatchingEventHandler(fcmMatchingSystem));
        Room room = user.createRoom(toPositionList(request.getRoomPositionList()),
                request.getName(),
                request.getPossibleOfflineArea());
        roomRepository.save(room);
        roomPositionRepository.saveAll(room.getRequiredPositionList());
        userRoomRepository.save(new UserRoom(user, room, null));
        return room.getId();
    }

    private void checkOwnerHaveAnotherOpenRoom(User user) {
        final Optional<Room> room = roomRepository.findOpenedRoomByOwner(user.getId());
        if (room.isPresent()) {
            throw new RoomNotCreateException("user already have another open room");
        }
    }

    private List<RoomPosition> toPositionList(List<RoomPositionDto> roomPositionList) {
        return roomPositionList.stream()
                .map(dto -> new RoomPosition(dto.getPosition(), dto.getCount()))
                .collect(Collectors.toList());
    }
}
