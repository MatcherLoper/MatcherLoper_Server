package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import com.toy.matcherloper.event.dispatcher.Events;
import com.toy.matcherloper.event.handler.SubscribeEventHandler;
import com.toy.matcherloper.event.handler.UnSubscribeEventHandler;
import com.toy.matcherloper.event.message.SubscribeEvent;
import com.toy.matcherloper.matching.fcm.FcmSubscribeService;
import com.toy.matcherloper.matching.type.TopicType;
import com.toy.matcherloper.web.room.exception.RoomNotFoundException;
import com.toy.matcherloper.web.room.service.RoomFindService;
import com.toy.matcherloper.web.user.exception.UserNotJoinRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserJoinRoomService {

    private final UserFindService userFindService;
    private final RoomFindService roomFindService;
    private final UserRoomRepository userRoomRepository;
    private final FcmSubscribeService fcmSubscribeService;

    @Transactional
    public Long join(Long userId, PositionType position) {
        final User user = userFindService.findById(userId);
        if (!user.canJoin()) {
            throw new UserNotJoinRoomException(String.format("User can't join room. user role: %s", user.getRoleType()));
        }
        Events.handleAsync(new SubscribeEventHandler(fcmSubscribeService));
        user.changeStatusMatching(RoleType.MATCHING);
        Events.raise(new SubscribeEvent(user.getDeviceToken(), TopicType.MATCHING.getToken()));
        final List<Room> openRooms = roomFindService.findAllByOpenWithUser();

        final Room canJoinRoom = openRooms.stream()
                .filter(room -> room.canJoin(position))
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException("There are no rooms available to participate"));
        Events.handleAsync(new UnSubscribeEventHandler(fcmSubscribeService));
        canJoinRoom.joinUser(user, position);

        final UserRoom userRoom = userRoomRepository.save(new UserRoom(user, canJoinRoom, position));
        return userRoom.getId();
    }
}
