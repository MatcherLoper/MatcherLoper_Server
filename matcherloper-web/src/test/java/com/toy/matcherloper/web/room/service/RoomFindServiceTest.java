package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.exception.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class RoomFindServiceTest {

    @Autowired
    RoomCreateService roomCreateService;

    @Autowired
    RoomFindService roomFindService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    private Long roomId;

    @BeforeEach
    void init() {
        saveData();
    }

    @Test
    @DisplayName("id를 통한 Room 조회")
    public void findOneRoomWithUser_test() throws Exception {
        //given
        Room findOne = roomFindService.findByIdWithUser(roomId);

        //when

        //then
        assertThat(findOne.getId()).isEqualTo(roomId);
    }

    @Test
    @DisplayName("모든 방을 조회할때 getCreateRoomRequest()에 의해 만들어진 방도 포함하는지 조회")
    public void findAllRoomTest() throws Exception {
        //given
        List<Room> allWithUser = roomFindService.findAllWithUser();
        //when

        //then
        assertThat(allWithUser).extracting(Room::getName).contains("room1");
    }


    @ParameterizedTest
    @CsvSource("1000")
    @DisplayName("존재하지 않는 Room을 조회하면 오류가 발생한다.")
    public void roomNotFoundTest(Long notExistRoomId) throws Exception {
        //given

        //when

        //then
        assertThatThrownBy(() -> roomFindService.findByIdWithUser(notExistRoomId))
                .isInstanceOf(RoomNotFoundException.class);
    }

    private void saveData() {

        User user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("진민")
                .phoneNumber("010-1234-1234")
                .introduction("test")
                .roleType(RoleType.OWNER)
                .address(new Address("부", "원"))
                .userPositionSet(Collections.singleton(new UserPosition(PositionType.BACKEND)))
                .skillSet(Collections.singleton(new Skill("spring")))
                .build();

        User savedUser = userRepository.save(user);

        roomId = roomCreateService.create(getCreateRoomRequest(savedUser));
    }

    private CreateRoomRequest getCreateRoomRequest(User user) {
        RoomPosition roomPosition = new RoomPosition(PositionType.BACKEND, false);
        RoomPositionDto roomPositionDto = new RoomPositionDto(roomPosition);
        return new CreateRoomRequest(user.getId(),
                Collections.singletonList(roomPositionDto), "room1", "부", 4);
    }
}