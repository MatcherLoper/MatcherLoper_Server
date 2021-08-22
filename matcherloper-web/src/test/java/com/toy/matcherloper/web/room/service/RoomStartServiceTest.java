package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomStartServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomFindService roomFindService;

    @Autowired
    private RoomStartService roomStartService;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Test
    @DisplayName("방이 프로젝트를 시작하면 방 상태가 START로 변경")
    public void startTest() throws Exception {
        //given
        Long savedRoomId = saveRoom();

        //when
        Long startRoomId = roomStartService.start(savedRoomId);
        Room findRoom = roomFindService.findOne(startRoomId);

        //then
        assertThat(findRoom.getStatus()).isEqualTo(RoomStatus.START);
    }

    private Long saveRoom() {
        User user1 = User.builder()
                .address(new Address("1", "1"))
                .email("test@test.com")
                .introduction("")
                .name("ㅋㅋ")
                .password("1234")
                .phoneNumber("1-1-1")
                .roleType(RoleType.NONE)
                .build();
        User user2 = User.builder()
                .address(new Address("2", "21"))
                .email("test2@test.com")
                .introduction("")
                .name("ㅋㅋ")
                .password("4321")
                .phoneNumber("2-2-2")
                .roleType(RoleType.NONE)
                .build();
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);
        userRepository.saveAll(userSet);

        final Room room = new Room(user1.getId(), Arrays.asList(RoomPosition.builder()
                .position(PositionType.BACKEND)
                .count(2)
                .build()), "test room", "부천");

        final UserRoom userRoom1 = new UserRoom(user1, room, null);
        final UserRoom userRoom2 = new UserRoom(user2, room, null);
        room.addUserRoom(userRoom1);
        room.addUserRoom(userRoom2);

        Room savedRoom = roomRepository.save(room);
        userRoomRepository.save(userRoom1);
        userRoomRepository.save(userRoom2);

        return savedRoom.getId();
    }
}
