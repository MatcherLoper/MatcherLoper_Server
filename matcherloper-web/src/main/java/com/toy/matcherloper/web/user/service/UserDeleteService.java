package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserFindService userFindService;
    private final UserRepository userRepository;

    @Transactional
    public Long delete(Long userId) {
        User user = userFindService.findById(userId);

        userRepository.delete(user);

        return user.getId();
    }
}
