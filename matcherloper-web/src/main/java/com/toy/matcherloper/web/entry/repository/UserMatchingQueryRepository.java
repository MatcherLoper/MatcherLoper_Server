package com.toy.matcherloper.web.entry.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.toy.matcherloper.core.user.model.QUser.user;
import static com.toy.matcherloper.core.user.model.QUserPosition.userPosition;
import static com.toy.matcherloper.web.utils.PositionTypeAndRoleTypeCheck.*;

@Repository
@RequiredArgsConstructor
public class UserMatchingQueryRepository {

    private final JPAQueryFactory queryFactory;

    public int getMatchingBackEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.BACKEND)
                        .and(isEqRoleType(RoleType.MATCHING)))
                .fetch()
                .size();
    }

    public int getMatchingFrontEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.FRONTEND)
                        .and(isEqRoleType(RoleType.MATCHING)))
                .fetch()
                .size();
    }

    public int getMatchingAndroidUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.ANDROID)
                        .and(isEqRoleType(RoleType.MATCHING)))
                .fetch()
                .size();
    }

    public int getMatchingIosUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionType(PositionType.IOS)
                        .and(isEqRoleType(RoleType.MATCHING)))
                .fetch()
                .size();
    }
}
