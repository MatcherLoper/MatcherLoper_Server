package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomStartServiceTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomFindService roomFindService;

    @Autowired
    RoomStartService roomStartService;

    private User user;

    @BeforeEach
    void init(){
        save();
    }

    @Test
    @DisplayName("방이 프로젝트를 시작하면 방 상태가 CLOSED로 변경")
    public void startRoomTest() throws Exception {
        //given
        Long savedRoomId = saveRoom();

        //when
        Long startRoomId = roomStartService.start(savedRoomId);
        Room findRoom = roomFindService.findByIdWithUser(startRoomId);

        //then
        assertThat(findRoom.getStatus()).isEqualTo(RoomStatus.CLOSED);
    }

    private void save() {
        user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("진민")
                .phoneNumber("010-1234-1234")
                .introduction("test")
                .roleType(RoleType.NONE)
                .address(new Address("부", "원"))
                .userPositionSet(Collections.singleton(new UserPosition(PositionType.BACKEND)))
                .skillSet(Collections.singleton(new Skill("spring")))
                .build();

        userRepository.save(user);
    }

    private Long saveRoom() {
        Room room = Room.builder()
                .name("test Room")
                .possibleOfflineArea("부천")
                .requiredUserNumber(4)
                .status(RoomStatus.OPEN)
                .build();

        Room savedRoom = roomRepository.save(room);

        User user1 = User.builder()
                .address(new Address("1", "1"))
                .email("test@test.com")
                .introduction("")
                .name("ㅋㅋ")
                .room(room)
                .password("1234")
                .phoneNumber("1-1-1")
                .roleType(RoleType.NONE)
                .build();

        User user2 = User.builder()
                .address(new Address("2", "21"))
                .email("test2@test.com")
                .introduction("")
                .name("ㅋㅋ")
                .room(room)
                .password("4321")
                .phoneNumber("2-2-2")
                .roleType(RoleType.NONE)
                .build();

        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);

        userRepository.saveAll(userSet);

        return savedRoom.getId();
    }
}