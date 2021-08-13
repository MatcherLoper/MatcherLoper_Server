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
class RoomCloseServiceTest {

    @Autowired
    private RoomCloseService roomCloseService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomFindService roomFindService;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Test
    @DisplayName("프로젝트가 끝나면 방 상태가 CLOSED로 변경된다.")
    public void closeTest() throws Exception {
        //given
        final Long roomId = saveRoom();

        //when
        final Long closeRoomId = roomCloseService.close(roomId);
        final Room actual = roomFindService.findOne(closeRoomId);
        final Set<UserRoom> userRooms = actual.getUserRooms();

        //then
        assertThat(actual.getStatus()).isEqualTo(RoomStatus.CLOSED);
        assertThat(userRooms).extracting(userRoom -> userRoom.getUser().getRoleType()).containsOnly(RoleType.NONE);
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
                .roleType(RoleType.PARTICIPANT)
                .build();

        User user2 = User.builder()
                .address(new Address("2", "21"))
                .email("test2@test.com")
                .introduction("")
                .name("ㅋㅋ")
                .password("4321")
                .phoneNumber("2-2-2")
                .roleType(RoleType.PARTICIPANT)
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