package com.toy.matcherloper.web.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.matcherloper.core.room.model.QRoom.room;

@Repository
@RequiredArgsConstructor
public class RoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Room> findAllWithOwnerAndParticipant() {
        return queryFactory.selectFrom(room)
                .distinct()
                .innerJoin(room.owner).fetchJoin()
                .innerJoin(room.participantSet).fetchJoin()
                .innerJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }

    public List<Room> findAllByOpenWithOwnerAndParticipant() {
        return queryFactory.selectFrom(room)
                .distinct()
                .where(room.status.eq(RoomStatus.OPEN))
                .innerJoin(room.owner).fetchJoin()
                .innerJoin(room.participantSet).fetchJoin()
                .innerJoin(room.requiredPositionList).fetchJoin()
                .fetch();
    }
}
