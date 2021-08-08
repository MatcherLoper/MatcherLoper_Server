package com.toy.matcherloper.web.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.toy.matcherloper.core.room.model.QRoom.room;


@Repository
@RequiredArgsConstructor
public class RoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Room> findAllWithUser() {
        return queryFactory.selectFrom(room)
                .innerJoin(room.userSet).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public List<Room> findAllByOpenWithUser() {
        return queryFactory.selectFrom(room)
                .where(room.status.eq(RoomStatus.OPEN))
                .innerJoin(room.userSet).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public Optional<Room> findByIdWithUser(Long roomId) {
        return Optional.ofNullable(queryFactory.selectFrom(room)
                .where(room.id.eq(roomId))
                .innerJoin(room.userSet).fetchJoin()
                .leftJoin(room.requiredPositionList).fetchJoin()
                .fetchOne());
    }
}
