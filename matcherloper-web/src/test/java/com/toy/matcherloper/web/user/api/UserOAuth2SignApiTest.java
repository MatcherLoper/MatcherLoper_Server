package com.toy.matcherloper.web.user.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2AddProfileRequest;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2FirstLoginRequest;
import com.toy.matcherloper.web.user.service.UserFindService;
import com.toy.matcherloper.web.user.service.oauth.UserOAuth2SaveService;
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

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserOAuth2SignApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserOAuth2SaveService userOAuth2SaveService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFindService userFindService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("Google로 로그인한 유저의 정보를 저장한다.")
    public void saveOAuth2UserTest() throws Exception {
        //given
        OAuth2FirstLoginRequest loginRequest = new OAuth2FirstLoginRequest("test@test.com", "test", "abcd");
        Long firstSavedUserId = userOAuth2SaveService.saveOAuth2User(loginRequest);
        User savedUser = userFindService.findById(firstSavedUserId);

        OAuth2AddProfileRequest addRequest = new OAuth2AddProfileRequest("010-1111-1111", "abcd",
                new ArrayList<>(), new ArrayList<>(), new AddressDto());
        String requestJson = objectMapper.writeValueAsString(addRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/users/oauth2/" + savedUser.getEmail() + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ApiResult<Long> longApiResult = objectMapper.readValue(responseJson, new TypeReference<ApiResult<Long>>() {
        });
        User user = userFindService.findById(longApiResult.getData());

        //then
        assertThat(user.getAuthProvider()).isEqualTo(AuthProviderType.google);
        assertThat(user.getPhoneNumber()).isEqualTo("010-1111-1111");
    }

    @Test
    @DisplayName("Google로 로그인한 유저의 정보 추가한다.")
    public void addProfileOAuth2UserTest() throws Exception {
        //given
        OAuth2FirstLoginRequest loginRequest = new OAuth2FirstLoginRequest("test@test.com", "test", "abcd");

        String requestJson = objectMapper.writeValueAsString(loginRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/users/oauth2/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ApiResult<Long> longApiResult = objectMapper.readValue(responseJson, new TypeReference<ApiResult<Long>>() {
        });
        User user = userFindService.findById(longApiResult.getData());

        //then
        assertThat(user.getAuthProvider()).isEqualTo(AuthProviderType.google);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
    }
}