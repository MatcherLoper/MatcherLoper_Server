package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.exception.PasswordNotMatchedException;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.exception.EmailDuplicateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserSignServiceTest {

    @Autowired
    private UserSignService loginService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저를 저장한다.")
    public void signUp() throws Exception {
        //given
        final SignUpRequest signUpRequest = new SignUpRequest("test@test.com", "1234", "user", "1234-5678", "hi",
                new ArrayList<>(), new ArrayList<>(), new AddressDto(new Address("인천", "아파트")));

        //when
        final Long userId = loginService.signUp(signUpRequest);
        final User user = userRepository.findById(userId).get();

        //then
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("회원가입시 이메일이 중복되면 EmailDuplicateException이 발생한다.")
    public void signUpExceptionTest() throws Exception {
        //given
        final SignUpRequest signUpRequest = new SignUpRequest("test@test.com", "1234", "user", "1234-5678", "hi",
                new ArrayList<>(), new ArrayList<>(), new AddressDto(new Address("인천", "아파트")));
        loginService.signUp(signUpRequest);

        //when
        assertThatThrownBy(() -> loginService.signUp(signUpRequest))
                .isInstanceOf(EmailDuplicateException.class)
                .hasMessage(String.format("%s is duplicated email", signUpRequest.getEmail()));
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하면 로그인이 성공한다.")
    public void signInTest() throws Exception {
        //given
        final SignUpRequest signUpRequest = new SignUpRequest("test@test.com", "1234", "user", "1234-5678", "hi",
                new ArrayList<>(), new ArrayList<>(), new AddressDto(new Address("인천", "아파트")));
        loginService.signUp(signUpRequest);

        //when
        final Long loginUserId = loginService.signIn(new SignInRequest(signUpRequest.getEmail(), signUpRequest.getPassword()));

        //then
        assertThat(loginUserId).isEqualTo(1L);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 PasswordNotMatchedException이 발생한다.")
    public void signInExceptionTest() throws Exception {
        //given
        final SignUpRequest signUpRequest = new SignUpRequest("test@test.com", "1234", "user", "1234-5678", "hi",
                new ArrayList<>(), new ArrayList<>(), new AddressDto(new Address("인천", "아파트")));
        loginService.signUp(signUpRequest);
        final SignInRequest signInRequest = new SignInRequest(signUpRequest.getEmail(), signUpRequest.getPassword() + "1");

        //when
        assertThatThrownBy(() -> loginService.signIn(signInRequest))
                .isInstanceOf(PasswordNotMatchedException.class)
                .hasMessage("Password is not matched!");
    }
}