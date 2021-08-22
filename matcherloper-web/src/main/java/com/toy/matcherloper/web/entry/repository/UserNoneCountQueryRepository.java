package com.toy.matcherloper.web.entry.repository;

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
                .where(userPosition.type.eq(PositionType.BACKEND)
                        .and(user.roleType.eq(RoleType.NONE)))
                .fetch()
                .size();
        return backEndUserCnt;
    }

    public int getFrontEndUserCnt() {
        int frontEndUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(userPosition.type.eq(PositionType.FRONTEND)
                        .and(user.roleType.eq(RoleType.NONE)))
                .fetch()
                .size();
        return frontEndUserCnt;
    }

    public int getAndroidUserCnt() {
        int androidUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(userPosition.type.eq(PositionType.ANDROID)
                        .and(user.roleType.eq(RoleType.NONE)))
                .fetch()
                .size();
        return androidUserCnt;
    }

    public int getIosUserCnt() {
        int iosUserCnt = queryFactory.selectFrom(user)
                .distinct()
                .innerJoin(user.userPositionSet, userPosition).fetchJoin()
                .where(userPosition.type.eq(PositionType.IOS)
                        .and(user.roleType.eq(RoleType.NONE)))
                .fetch()
                .size();
        return iosUserCnt;
    }
}
