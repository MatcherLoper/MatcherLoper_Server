package com.toy.matcherloper.web.room.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomStatus;
import com.toy.matcherloper.core.room.model.UserRoom;
import com.toy.matcherloper.core.room.repository.RoomRepository;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.core.user.repository.UserRoomRepository;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.api.dto.response.RoomFindResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomFindApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
        saveData();
    }

    @Test
    @DisplayName("??? id??? ?????? ??? ??????")
    public void findOneTest() throws Exception {
        //given
        Long roomId = room.getId();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/rooms/" + roomId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseStr = mvcResult.getResponse().getContentAsString();
        ApiResult<RoomFindResponse> result = objectMapper.readValue(responseStr, new TypeReference<ApiResult<RoomFindResponse>>() {
        });

        //then
        assertThat(result.getData().getRoomId()).isEqualTo(roomId);
    }

    @Test
    @DisplayName("?????? ??? ??????, ????????? ?????? ????????? List ??? ???????????? ??????")
    public void findAllTest() throws Exception {
        //given
        Long roomId = room.getId();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/rooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseStr = mvcResult.getResponse().getContentAsString();
        ApiResult<List<RoomFindResponse>> result = objectMapper.readValue(responseStr, new TypeReference<ApiResult<List<RoomFindResponse>>>() {
        });

        //then
        assertThat(result.getData()).extracting(RoomFindResponse::getRoomId).contains(roomId);
    }

    private void saveData() {
        room = Room.builder()
                .name("test Room")
                .possibleOfflineArea("??????")
                .status(RoomStatus.OPEN)
                .build();

        roomRepository.save(room);

        User user1 = User.builder()
                .address(new Address("1", "1"))
                .email("test@test.com")
                .introduction("")
                .name("??????")
                .password("1234")
                .phoneNumber("1-1-1")
                .roleType(RoleType.NONE)
                .build();

        User user2 = User.builder()
                .address(new Address("2", "21"))
                .email("test2@test.com")
                .introduction("")
                .name("??????")
                .password("4321")
                .phoneNumber("2-2-2")
                .roleType(RoleType.NONE)
                .build();

        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);

        userRepository.saveAll(userSet);

        userRoomRepository.save(new UserRoom(user1, room, null));
        userRoomRepository.save(new UserRoom(user2, room, null));
    }
}