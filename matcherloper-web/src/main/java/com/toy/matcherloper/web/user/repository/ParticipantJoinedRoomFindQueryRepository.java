package com.toy.matcherloper.web.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.user.model.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.matcherloper.core.room.model.QRoom.room;
import static com.toy.matcherloper.core.user.model.QParticipant.participant;

@Repository
@RequiredArgsConstructor
public class ParticipantJoinedRoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Participant> findParticipantListByRoom(Long roomId) {
        return queryFactory.selectFrom(participant)
                .join(participant.room, room)
                .where(participant.room.id.eq(roomId))
                .fetch();
    }
}
