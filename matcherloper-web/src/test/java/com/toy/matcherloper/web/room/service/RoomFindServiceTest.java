package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import com.toy.matcherloper.web.room.exception.RoomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class RoomFindServiceTest {

    @Autowired
    private RoomFindService roomFindService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Test
    @DisplayName("id를 통한 Room 조회")
    public void findOneRoomWithUserTest() throws Exception {
        //given
        Long roomId = saveRoom();
        Room findOne = roomFindService.findOne(roomId);

        //when

        //then
        assertThat(findOne.getId()).isEqualTo(roomId);
    }

    @Test
    @DisplayName("모든 방을 조회, 조회시 저장된 데이터의 룸이 포함된지 확인")
    public void findAllRoomTest() throws Exception {
        //given
        Long roomId = saveRoom();
        List<Room> allWithUser = roomFindService.findAllWithUser();
        //when

        //then
        assertThat(allWithUser).extracting(Room::getName).contains("test Room");
    }


    @ParameterizedTest
    @CsvSource("1000")
    @DisplayName("존재하지 않는 Room을 조회하면 오류가 발생한다.")
    public void roomNotFoundTest(Long notExistRoomId) throws Exception {
        //given
        Long saveRoomId = saveRoom();
        //when

        //then
        assertThatThrownBy(() -> roomFindService.findOne(notExistRoomId))
                .isInstanceOf(RoomNotFoundException.class);
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