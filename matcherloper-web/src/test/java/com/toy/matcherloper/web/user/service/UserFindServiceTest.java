package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserFindServiceTest {

    @Autowired
    private UserFindService userFindService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        saveUser();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Id가 일치하는 User를 찾는다.")
    public void findByIdTest() throws Exception {
        //when
        final User actual = userFindService.findById(user.getId());

        //then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    @DisplayName("존재하지 않는 Id이면 UserNotFoundException이 발생한다.")
    public void findByIdExceptionTest() throws Exception {
        //given
        Long id = user.getId() + 1;

        //then
        assertThatThrownBy(() -> userFindService.findById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("존재하지 않는 사용자입니다. User id: %d", id));
    }

    @Test
    @DisplayName("Email이 일치하는 User를 찾는다.")
    public void findByEmailTest() throws Exception {
        //when
        final User actual = userFindService.findByEmail(user.getEmail());

        //then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    @DisplayName("존재하지 않는 Email이면 UserNotFoundException이 발생한다.")
    public void findByEmailExceptionTest() throws Exception {
        //given
        String email = user.getEmail() + "1";

        //then
        assertThatThrownBy(() -> userFindService.findByEmail(email))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("존재하지 않는 사용자입니다. User email: %s", email));
    }

    private void saveUser() {
        user = User.create("test@test.com", "1234", "user", "1234-5678", "hi",
                new HashSet<>(), new HashSet<>(), new Address("인천", "아파트;"));
        userRepository.save(user);
    }
}