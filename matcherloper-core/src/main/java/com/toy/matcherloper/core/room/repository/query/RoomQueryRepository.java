package com.toy.matcherloper.core.room.repository.query;

import com.toy.matcherloper.core.room.model.Room;

import java.util.Optional;

public interface RoomQueryRepository {
    Optional<Room> findOpenedRoomByOwner(Long ownerId);
}
