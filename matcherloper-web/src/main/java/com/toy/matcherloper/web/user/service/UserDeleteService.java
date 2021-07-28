package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
