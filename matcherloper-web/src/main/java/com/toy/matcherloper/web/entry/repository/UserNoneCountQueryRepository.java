package com.toy.matcherloper.web.entry.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.toy.matcherloper.core.user.model.QUser.user;
import static com.toy.matcherloper.core.user.model.QUserPosition.userPosition;
import static com.toy.matcherloper.web.entry.repository.spec.PositionTypeAndRoleTypeCheck.isEqPositionType;
import static com.toy.matcherloper.web.entry.repository.spec.PositionTypeAndRoleTypeCheck.isEqRoleType;

@Repository
@RequiredArgsConstructor
public class UserNoneCountQueryRepository {

    private final JPAQueryFactory queryFactory;

    public int getBackEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.BACKEND)
                        .and(isEqRoleType(RoleType.NONE)))
                .fetch()
                .size();
    }

    public int getFrontEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.FRONTEND)
                        .and(isEqRoleType(RoleType.NONE)))
                .fetch()
                .size();
    }

    public int getAndroidUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.ANDROID)
                        .and(isEqRoleType(RoleType.NONE)))
                .fetch()
                .size();
    }

    public int getIosUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.IOS)
                        .and(isEqRoleType(RoleType.NONE)))
                .fetch()
                .size();
    }
}
