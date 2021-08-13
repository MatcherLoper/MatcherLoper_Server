package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomCloseService {

    private final RoomFindService roomFindService;

    @Transactional
    public Long close(Long roomId) {
        final Room room = roomFindService.findOne(roomId);
        room.close();
        return room.getId();
    }
}
