package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomStartService {

    private final RoomFindService roomFindService;

    public Long start(Long roomId) {
        final Room room = roomFindService.findOne(roomId);
        room.start();
        return room.getId();
    }
}
