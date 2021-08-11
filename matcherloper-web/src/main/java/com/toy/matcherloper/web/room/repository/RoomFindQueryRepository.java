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
        queryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.user).fetchJoin()
                .fetch();

        return queryFactory.selectFrom(room)
                .distinct()
                .innerJoin(room.userRooms).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public List<Room> findAllByOpenWithUser() {
        queryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.user).fetchJoin()
                .where(userRoom.room.status.eq(RoomStatus.OPEN))
                .fetch();

        return queryFactory.selectFrom(room)
                .distinct()
                .where(room.status.eq(RoomStatus.OPEN))
                .innerJoin(room.userRooms).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public Optional<Room> findOne(Long roomId) {
        queryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.user).fetchJoin()
                .where(userRoom.room.id.eq(roomId))
                .fetch();

        return Optional.ofNullable(queryFactory.selectFrom(room)
                .distinct()
                .where(room.id.eq(roomId))
                .innerJoin(room.userRooms).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetchOne());
    }
}
