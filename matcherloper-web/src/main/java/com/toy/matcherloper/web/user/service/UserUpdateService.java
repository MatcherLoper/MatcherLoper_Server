package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.web.user.api.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindService userFindService;

    @Transactional
    public Long update(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userFindService.findById(userId);
        user.update(userUpdateRequest.getPassword(),
                userUpdateRequest.getName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getIntroduction(),
                toUserPositionSet(userUpdateRequest.getUserPositionDtoList()),
                toSkillSet(userUpdateRequest.getSkillDtoList()),
                toAddress(userUpdateRequest.getAddressDto()));
        return user.getId();
    }
}
