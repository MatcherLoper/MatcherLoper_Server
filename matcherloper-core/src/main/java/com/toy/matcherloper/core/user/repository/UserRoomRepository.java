package com.toy.matcherloper.core.user.repository;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
    Optional<UserRoom> findByUserAndRoom(User user, Room room);
}
