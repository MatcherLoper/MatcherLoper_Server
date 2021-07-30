package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFindService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다. User id = " + userId));
    }

    /**
     * 추후, Spring security 를 이용한 UsernameNotFoundException(email)
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
    }
}
