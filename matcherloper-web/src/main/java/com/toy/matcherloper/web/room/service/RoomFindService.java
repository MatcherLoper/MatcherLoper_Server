package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.web.room.exception.RoomNotFoundException;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import com.toy.matcherloper.web.room.repository.UserRoomQueryRepository;
import com.toy.matcherloper.web.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomFindService {

    private final RoomFindQueryRepository roomFindQueryRepository;
    private final UserRoomQueryRepository userRoomQueryRepository;
    private final UserFindService userFindService;

    public Room findOne(Long roomId) {
        return roomFindQueryRepository.findOne(roomId)
                .orElseThrow(() -> new RoomNotFoundException(String.format("Not found room. room id: %d", roomId)));
    }

    public List<Room> findAllWithUser() {
        return roomFindQueryRepository.findAllWithUser();
    }

    public List<Room> findAllByOpenWithUser() {
        return roomFindQueryRepository.findAllByOpenWithUser();
    }

    public List<UserRoom> findAllByUserInParticipated(Long userId) {
        return userRoomQueryRepository.findByUserAndRoomStatusClosedWithRoom(userFindService.findById(userId));
    }

    public UserRoom findAllByUserInParticipating(Long userId) {
        return userRoomQueryRepository.findByUserAndRoomStatusStartWithRoom(userFindService.findById(userId))
                .orElseThrow(() -> new RoomNotFoundException("not found user room!!"));
    }
}
