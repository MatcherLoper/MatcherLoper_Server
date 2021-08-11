package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomDeleteService {

    private final RoomRepository roomRepository;
    private final RoomFindService roomFindService;

    public Long delete(Long roomId) {
        final Room room = roomFindService.findOne(roomId);
        roomRepository.delete(room);
        return room.getId();
    }
}
