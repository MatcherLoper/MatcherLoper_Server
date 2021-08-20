package com.toy.matcherloper.web.user.service.oauth;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2FirstLoginRequest;
import com.toy.matcherloper.web.user.service.UserFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserOAuth2SaveServiceTest {

    @Autowired
    private UserOAuth2SaveService userOAuth2SaveService;

    @Autowired
    private UserFindService userFindService;

    @Test
    @DisplayName("Google 로 로그인 한 유저의 정보를 저장한다.")
    public void saveUserTest() throws Exception {
        //given
        OAuth2FirstLoginRequest request = new OAuth2FirstLoginRequest("test@test.com", "test", "adfasfasdf");
        Long userId = userOAuth2SaveService.saveOAuth2User(request);

        //when
        User user = userFindService.findById(userId);

        //then
        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getAuthProvider()).isEqualTo(AuthProviderType.google);
    }

}