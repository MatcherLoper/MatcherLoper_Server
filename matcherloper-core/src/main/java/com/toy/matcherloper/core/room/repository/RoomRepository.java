package com.toy.matcherloper.core.room.repository;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.user.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByOwner(Owner owner);
}
