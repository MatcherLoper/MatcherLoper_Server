package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomStartServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomFindService roomFindService;

    @Autowired
    RoomStartService roomStartService;

    @Autowired
    RoomCreateService roomCreateService;

    private User user;

    @BeforeEach
    void init(){
        save();
    }

    @Test
    @DisplayName("방이 프로젝트를 시작하면 방 상태가 CLOSED로 변경")
    public void startRoomTest() throws Exception {
        //given
        CreateRoomRequest createRoomRequest = getCreateRoomRequest(user);
        Long createRoomId = roomCreateService.create(createRoomRequest);

        //when
        Long startRoomId = roomStartService.start(createRoomId);
        Room findRoom = roomFindService.findByIdWithUser(startRoomId);

        //then
        assertThat(findRoom).extracting(Room::getStatus).contains(RoomStatus.CLOSED);
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

    private CreateRoomRequest getCreateRoomRequest(User user) {
        RoomPosition roomPosition = new RoomPosition(PositionType.BACKEND, false);
        RoomPositionDto roomPositionDto = new RoomPositionDto(roomPosition);
        return new CreateRoomRequest(user.getId(),
                Collections.singletonList(roomPositionDto), "room1", "부", 4);
    }
}