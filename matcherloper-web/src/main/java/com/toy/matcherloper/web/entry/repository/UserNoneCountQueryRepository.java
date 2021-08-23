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
public class UserNoneCountQueryRepository {

    private final JPAQueryFactory queryFactory;

    public int getBackEndUserCnt() {
        int backEndUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeNone(PositionType.BACKEND))
                .fetch()
                .size();
        return backEndUserCnt;
    }

    public int getFrontEndUserCnt() {
        int frontEndUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeNone(PositionType.FRONTEND))
                .fetch()
                .size();
        return frontEndUserCnt;
    }

    public int getAndroidUserCnt() {
        int androidUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeNone(PositionType.ANDROID))
                .fetch()
                .size();
        return androidUserCnt;
    }

    public int getIosUserCnt() {
        int iosUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(isEqPositionTypeAndUserRoleTypeNone(PositionType.IOS))
                .fetch()
                .size();
        return iosUserCnt;
    }

    private BooleanExpression isEqPositionTypeAndUserRoleTypeNone(PositionType positionType) {
        return userPosition.type.eq(positionType).and(user.roleType.eq(RoleType.NONE));
    }
}
