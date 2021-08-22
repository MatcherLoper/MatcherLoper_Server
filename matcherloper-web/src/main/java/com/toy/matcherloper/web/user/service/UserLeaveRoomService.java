package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import com.toy.matcherloper.event.dispatcher.Events;
import com.toy.matcherloper.event.handler.MatchingEventHandler;
import com.toy.matcherloper.matching.process.FcmMatchingSystem;
import com.toy.matcherloper.web.room.service.RoomFindService;
import com.toy.matcherloper.web.user.exception.UserRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLeaveRoomService {

    private final UserFindService userFindService;
    private final RoomFindService roomFindService;
    private final UserRoomRepository userRoomRepository;
    private final FcmMatchingSystem matchingSystem;

    @Transactional
    public Long leave(Long userId, Long roomId) {
        final User user = userFindService.findById(userId);
        final Room room = roomFindService.findOne(roomId);
        final UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room)
                .orElseThrow(() -> new UserRoomNotFoundException("Not found user room!!"));
        Events.handleAsync(new MatchingEventHandler(matchingSystem));
        userRoom.leaveUser();
        userRoomRepository.delete(userRoom);
        return user.getId();
    }
}
