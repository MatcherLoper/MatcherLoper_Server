package com.toy.matcherloper.core.room.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.toy.matcherloper.core.room.model.QRoom.room;

@Repository
@RequiredArgsConstructor
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Room> findOpenedRoomByOwner(Long ownerId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(room)
                        .where(room.createUserId.eq(ownerId)
                                .and(room.status.eq(RoomStatus.OPEN)))
                        .fetchOne());
    }
}
