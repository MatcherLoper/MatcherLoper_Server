package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        userRoomRepository.save(new UserRoom(user1, room));
        userRoomRepository.save(new UserRoom(user2, room));

        return savedRoom.getId();
    }
}
