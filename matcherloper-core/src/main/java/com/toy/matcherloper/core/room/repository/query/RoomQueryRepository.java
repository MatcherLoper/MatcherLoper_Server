package com.toy.matcherloper.core.room.repository.query;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.user.model.Owner;

import java.util.Optional;

public interface RoomQueryRepository {
    Optional<Room> findOneByOwner(Owner owner);
}
