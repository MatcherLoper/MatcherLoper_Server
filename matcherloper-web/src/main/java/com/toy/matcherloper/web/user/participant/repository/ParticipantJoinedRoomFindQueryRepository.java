package com.toy.matcherloper.web.user.participant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.toy.matcherloper.core.room.model.QUserRoom.userRoom;

@Repository
@RequiredArgsConstructor
public class ParticipantJoinedRoomFindQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<User> findParticipantListByRoom(Long roomId) {
        final List<UserRoom> userRooms = queryFactory.selectFrom(userRoom)
                .innerJoin(userRoom.user).fetchJoin()
                .innerJoin(userRoom.room).fetchJoin()
                .where(userRoom.room.id.eq(roomId))
                .fetch();

        return userRooms.stream()
                .filter(userRoom -> userRoom.getUser()
                        .getRoleType().equals(RoleType.PARTICIPANT))
                .map(UserRoom::getUser)
                .collect(Collectors.toList());
    }
}
