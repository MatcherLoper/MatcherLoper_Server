package com.toy.matcherloper.web.user.participant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.matcherloper.core.user.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class ParticipantJoinedRoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<User> findParticipantListByRoom(Long roomId) {
        return queryFactory.selectFrom(user)
                .where(user.room.id.eq(roomId)
                        .and(user.roleType.eq(RoleType.PARTICIPANT)))
                .fetch();
    }
}
