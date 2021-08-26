package com.toy.matcherloper.web.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.toy.matcherloper.core.room.model.QRoom.room;
import static com.toy.matcherloper.core.room.model.QUserRoom.userRoom;

@Repository
@RequiredArgsConstructor
public class UserRoomQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<UserRoom> findByUserAndRoomStatusClosedWithRoom(User user) {
        return jpaQueryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.room, room).fetchJoin()
                .where(userRoom.user.eq(user)
                        .and(userRoom.room.status.eq(RoomStatus.CLOSED)))
                .fetch();
    }

    public Optional<UserRoom> findByUserAndRoomStatusStartWithRoom(User user) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.room, room).fetchJoin()
                .where(userRoom.user.eq(user)
                        .and(userRoom.room.status.eq(RoomStatus.START)))
                .fetchOne());
    }
}
