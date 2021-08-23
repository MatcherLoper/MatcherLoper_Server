package com.toy.matcherloper.web.entry.repository.spec;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;

import static com.toy.matcherloper.core.user.model.QUser.user;
import static com.toy.matcherloper.core.user.model.QUserPosition.userPosition;

public class PositionTypeAndRoleTypeCheck {

    public static BooleanExpression isEqPositionType(PositionType positionType) {
        return userPosition.type.eq(positionType);
    }

    public static BooleanExpression isEqRoleType(RoleType roleType) {
        return user.roleType.eq(roleType);
    }
}
