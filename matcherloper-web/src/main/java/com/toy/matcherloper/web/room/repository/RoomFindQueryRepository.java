package com.toy.matcherloper.web.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.matcherloper.core.room.model.QRoom.*;

@Repository
@RequiredArgsConstructor
public class RoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Room> findAllWithOwnerAndParticipant() {
        return queryFactory.selectFrom(room)
                .innerJoin(room.owner)
                .innerJoin(room.participantList)
                .fetch();
    }

    public List<Room> findAllByOpenWithOwnerAndParticipant() {
        return queryFactory.selectFrom(room)
                .where(room.status.eq(RoomStatus.OPEN))
                .innerJoin(room.owner)
                .innerJoin(room.participantList)
                .fetch();
    }
}
