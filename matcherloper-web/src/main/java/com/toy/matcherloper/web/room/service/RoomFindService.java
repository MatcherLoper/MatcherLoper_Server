package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.web.room.exception.NotFoundRoomException;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomFindService {

    private final RoomFindQueryRepository roomFindQueryRepository;

    public Room findOne(Long roomId) {
        return roomFindQueryRepository.findByIdWithOwnerAndParticipant(roomId)
                .orElseThrow(() -> new NotFoundRoomException(String.format("Not found room. room id: %d", roomId)));
    }
}
