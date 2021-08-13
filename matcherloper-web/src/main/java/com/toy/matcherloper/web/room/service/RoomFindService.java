package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.web.room.exception.RoomNotFoundException;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomFindService {

    private final RoomFindQueryRepository roomFindQueryRepository;

    public Room findOne(Long roomId) {
        return roomFindQueryRepository.findOne(roomId)
                .orElseThrow(() -> new RoomNotFoundException(String.format("Not found room. room id: %d", roomId)));
    }

    public List<Room> findAllWithUser(){
        return roomFindQueryRepository.findAllWithUser();
    }

    public List<Room> findAllByOpenWithUser(){
        return roomFindQueryRepository.findAllByOpenWithUser();
    }
}