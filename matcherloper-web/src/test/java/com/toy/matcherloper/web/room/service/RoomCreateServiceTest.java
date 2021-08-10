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
import com.toy.matcherloper.web.room.exception.RoomNotCreateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RoomCreateServiceTest {

    @Autowired
    RoomCreateService roomCreateService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    private User user;

    @BeforeEach
    public void init() {
        saveData();
    }

    @Test
    @DisplayName("방을 개설한다.")
    public void create() throws Exception {
        //given
        CreateRoomRequest createRoomRequest = getCreateRoomRequest();

        //when
        final Long createdRoomId = roomCreateService.create(createRoomRequest);
        final Room room = roomRepository.findById(createdRoomId).get();

        //then
        assertThat(room.getName()).isEqualTo(createRoomRequest.getName());
        assertThat(room.getCreateUserId()).isEqualTo(createRoomRequest.getUserId());
        assertThat(room.getRequiredUserNumber()).isEqualTo(createRoomRequest.getRequiredUserNumber());
    }

    @Test
    @DisplayName("방장이 다른 방을 가지고 있을 때, 방을 생성하면 오류가 발생한다.")
    public void checkOwnerHaveAnotherOpenRoom() throws Exception {
        //given
        CreateRoomRequest createRoomRequest = getCreateRoomRequest();

        //when
        roomCreateService.create(createRoomRequest);

        //then
        assertThatThrownBy(() -> roomCreateService.create(createRoomRequest))
                .isInstanceOf(RoomNotCreateException.class);
    }

    private void saveData() {
        user = User.builder()
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

        userRepository.save(user);
    }

    private CreateRoomRequest getCreateRoomRequest() {
        RoomPosition roomPosition = new RoomPosition(PositionType.BACKEND, false);
        RoomPositionDto roomPositionDto = new RoomPositionDto(roomPosition);
        return new CreateRoomRequest(user.getId(),
                Collections.singletonList(roomPositionDto), "room1", "부", 4);
    }
}