package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.web.room.exception.NotFoundRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomFindService {

    private final RoomRepository roomRepository;

    public Room findOne(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundRoomException(String.format("Not found room. room id: %d", roomId)));
    }
}
