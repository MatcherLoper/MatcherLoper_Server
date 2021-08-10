package com.toy.matcherloper.web.room.service;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.*;
import com.toy.matcherloper.core.user.repository.OwnerRepository;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RoomCreateServiceTest {

    @Autowired
    RoomCreateService roomCreateService;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    RoomRepository roomRepository;

    private Owner owner;

    @BeforeEach
    public void init(){
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
        assertThat(room.getOwner().getId()).isEqualTo(createRoomRequest.getOwnerId());
        assertThat(room.getRequiredUserNumber()).isEqualTo(createRoomRequest.getRequiredUserNumber());
    }

    @Test
    @DisplayName("방장이 또 다른 방을 가지고 있을 때, 오류가 발생한다.")
    public void checkOwnerHaveAnotherOpenRoom() throws Exception {
        //given
        CreateRoomRequest createRoomRequest = getCreateRoomRequest();

        //when
        final Long createdRoomId1 = roomCreateService.create(createRoomRequest);
        final Room room1 = roomRepository.findById(createdRoomId1).get();

        final Long createdRoomId2 = roomCreateService.create(createRoomRequest);
        final Room room2 = roomRepository.findById(createdRoomId2).get();

        //then
        assertThat(room1.getOwner().getId()).isEqualTo(room2.getOwner().getId());
        assertThat(room1.getStatus().toString()).isEqualTo(room2.getStatus().toString());
    }

    private void saveData(){
        owner = Owner.ownerBuilder()
                .name("진민")
                .email("test@test.com")
                .phoneNumber("010-1234-1234")
                .password("1234")
                .introduction("test")
                .address(new Address("부", "원"))
                .userPositionSet(Collections.singleton(new UserPosition(PositionType.BACKEND)))
                .skillSet(Collections.singleton(new Skill("spring")))
                .build();

        ownerRepository.save(owner);
    }

    private CreateRoomRequest getCreateRoomRequest() {
        RoomPosition roomPosition = new RoomPosition(PositionType.BACKEND, false);
        RoomPositionDto roomPositionDto = new RoomPositionDto(roomPosition);
        return new CreateRoomRequest(owner.getId(),
                Collections.singletonList(roomPositionDto), "room1", "부", 4);
    }
}