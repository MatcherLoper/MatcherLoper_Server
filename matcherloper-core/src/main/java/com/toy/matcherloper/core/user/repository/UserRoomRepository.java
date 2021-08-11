package com.toy.matcherloper.core.user.repository;

import com.toy.matcherloper.core.room.model.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
}
