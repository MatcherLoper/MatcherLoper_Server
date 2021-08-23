package com.toy.matcherloper.web.entry.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.toy.matcherloper.core.user.model.QUser.user;
import static com.toy.matcherloper.core.user.model.QUserPosition.userPosition;

@Repository
@RequiredArgsConstructor
public class UserMatchingQueryRepository {

    private final JPAQueryFactory queryFactory;

    public int getMatchingBackEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeMatching(PositionType.BACKEND))
                .fetch()
                .size();
    }

    public int getMatchingFrontEndUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeMatching(PositionType.FRONTEND))
                .fetch()
                .size();
    }

    public int getMatchingAndroidUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeMatching(PositionType.ANDROID))
                .fetch()
                .size();
    }

    public int getMatchingIosUserCnt() {
        return queryFactory.selectFrom(user)
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeMatching(PositionType.IOS))
                .fetch()
                .size();
    }

    private BooleanExpression isEqPositionTypeAndUserRoleTypeMatching(PositionType positionType) {
        return userPosition.type.eq(positionType).and(user.roleType.eq(RoleType.MATCHING));
    }
}
