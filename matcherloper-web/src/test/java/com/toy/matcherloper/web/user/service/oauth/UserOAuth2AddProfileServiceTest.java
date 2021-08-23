package com.toy.matcherloper.web.user.service.oauth;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2AddProfileRequest;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2FirstLoginRequest;
import com.toy.matcherloper.web.user.service.UserFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserOAuth2AddProfileServiceTest {

    @Autowired
    private UserOAuth2SaveService userOAuth2SaveService;

    @Autowired
    private UserFindService userFindService;

    @Autowired
    private UserOAuth2AddProfileService userOAuth2AddProfileService;

    @Test
    @DisplayName("Google 로 로그인 한 유저의 추가 정보를 입력하여 저장한다.")
    public void addProfileOAuth2UserTest() throws Exception {
        //given
        OAuth2AddProfileRequest addRequest = new OAuth2AddProfileRequest("010-1111-1111", "test", new ArrayList<>(), new ArrayList<>(), new AddressDto());
        Long saveUserId = saveUser();

        //when
        User beforeSaveUser = userFindService.findById(saveUserId);
        Long userId = userOAuth2AddProfileService.addProfileOAuth2User(beforeSaveUser.getEmail(), addRequest);
        User afterSaveUser = userFindService.findById(userId);

        //then
        assertThat(saveUserId).isEqualTo(userId);
        assertThat(afterSaveUser.getPhoneNumber()).isEqualTo("010-1111-1111");
    }

    private Long saveUser() {
        OAuth2FirstLoginRequest loginRequest = new OAuth2FirstLoginRequest("test@test.com", "test", "abcd");
        return userOAuth2SaveService.saveOAuth2User(loginRequest);
    }
}