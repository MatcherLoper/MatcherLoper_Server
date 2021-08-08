package com.toy.matcherloper.core.room.repository;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.repository.query.RoomQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomQueryRepository {
}
