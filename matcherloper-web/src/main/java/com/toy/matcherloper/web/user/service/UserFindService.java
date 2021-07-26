package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRepository;
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
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    /*public List<User> findAll() {
        return userRepository.findAll();
    }*/

    /**
     * 추후, Spring security -> UsernameNotFoundException(email)
     */
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(NoSuchElementException::new);
    }
}
