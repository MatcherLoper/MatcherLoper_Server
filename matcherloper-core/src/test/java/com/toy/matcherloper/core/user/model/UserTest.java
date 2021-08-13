package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @ParameterizedTest
    @DisplayName("User의 패스워드와 입력받은 패스워드가 일치하지 않으면 true, 일치하면 false를 리턴한다.")
    @CsvSource(value = {"1234, 123, true", "1234, 1234, false"})
    public void isNotMatchingPassword(String password, String inputPassword, boolean expected) {
        //given
        final User user = User.builder()
                .email("test@test.com")
                .password(password)
                .name("test")
                .phoneNumber("1234-5678")
                .roleType(RoleType.NONE)
                .build();

        //when
        final boolean actual = user.isNotMatchingPassword(inputPassword);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("방을 생성한 User의 타입은 Owner가 된다.")
    public void createRoomTest() {
        //given
        final User user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("test")
                .phoneNumber("1234-5678")
                .roleType(RoleType.NONE)
                .build();

        //when
        user.createRoom(Arrays.asList(new RoomPosition(PositionType.BACKEND, 2)), "test", 1, "인천");

        //then
        assertThat(user.getRoleType()).isEqualTo(RoleType.OWNER);
    }
}