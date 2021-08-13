package com.toy.matcherloper.web.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.toy.matcherloper.core.room.model.QRoom.room;
import static com.toy.matcherloper.core.room.model.QUserRoom.userRoom;


@Repository
@RequiredArgsConstructor
public class RoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Room> findAllWithUser() {
        return queryFactory.selectFrom(room)
                .distinct()
                .innerJoin(room.userRooms, userRoom).fetchJoin()
                .innerJoin(userRoom.user).fetchJoin()
                .innerJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public List<Room> findAllByOpenWithUser() {
        return queryFactory.selectFrom(room)
                .distinct()
                .innerJoin(room.userRooms, userRoom).fetchJoin()
                .innerJoin(userRoom.user).fetchJoin()
                .innerJoin(room.requiredPositionList).fetchJoin()
                .where(room.status.eq(RoomStatus.OPEN))
                .fetch();
    }

    public Optional<Room> findOne(Long roomId) {
        return Optional.ofNullable(queryFactory.selectFrom(room)
                .distinct()
                .innerJoin(room.userRooms, userRoom).fetchJoin()
                .innerJoin(userRoom.user).fetchJoin()
                .innerJoin(room.requiredPositionList).fetchJoin()
                .where(room.id.eq(roomId))
                .fetchOne());
    }
}
