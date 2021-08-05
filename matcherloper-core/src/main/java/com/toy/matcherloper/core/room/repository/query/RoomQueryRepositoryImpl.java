package com.toy.matcherloper.core.room.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.user.model.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.toy.matcherloper.core.room.model.QRoom.room;

@Repository
@RequiredArgsConstructor
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Room> findOneByOwner(Owner owner) {
        return Optional.ofNullable(
                queryFactory.selectFrom(room)
                        .where(room.owner.eq(owner))
                        .fetchOne());
    }
}
