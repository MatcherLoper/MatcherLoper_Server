package com.toy.matcherloper.web.room.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.core.room.model.RoomPosition;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.model.type.PositionType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.api.dto.RoomPositionDto;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.service.RoomFindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomCreateApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomFindService roomFindService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("?????? ??????.")
    public void roomCreateApiTest() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("??????")
                .phoneNumber("010-1234-1234")
                .introduction("test")
                .roleType(RoleType.OWNER)
                .address(new Address("???", "???"))
                .userPositionSet(Collections.singleton(new UserPosition(PositionType.BACKEND)))
                .skillSet(Collections.singleton(new Skill("spring")))
                .build();

        userRepository.save(user);

        RoomPosition roomPosition = new RoomPosition(PositionType.BACKEND, 2);
        RoomPositionDto roomPositionDto = new RoomPositionDto(roomPosition);
        CreateRoomRequest createRoomRequest = new CreateRoomRequest(user.getId(),
                Collections.singletonList(roomPositionDto), "room1", "???");

        String requestJson = objectMapper.writeValueAsString(createRoomRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();

        ApiResult<Long> longApiResult = objectMapper.readValue(responseString, new TypeReference<ApiResult<Long>>() {
        });

        Room createRoomFind = roomFindService.findOne(longApiResult.getData());

        //then
        assertThat(createRoomFind.getCreateUserId()).isEqualTo(user.getId());
    }
}