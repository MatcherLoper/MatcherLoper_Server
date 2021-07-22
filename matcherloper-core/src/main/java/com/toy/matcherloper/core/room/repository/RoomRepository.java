package com.toy.matcherloper.core.room.repository;

import com.toy.matcherloper.core.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
