package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindService userFindService;

    @Transactional
    public Long update(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userFindService.findById(userId);
        user.update(userUpdateRequest.getEmail(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getIntroduction(),
                toUserPositionSet(userUpdateRequest),
                toSkillSet(userUpdateRequest),
                toAddress(userUpdateRequest.getAddressDto()));
        return user.getId();
    }

    private Set<UserPosition> toUserPositionSet(UserUpdateRequest userUpdateRequest) {
        return userUpdateRequest.getUserPositionDtoList().stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toSet());
    }

    private Set<Skill> toSkillSet(UserUpdateRequest userUpdateRequest) {
        return userUpdateRequest.getSkillDtoList().stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toSet());
    }

    private Address toAddress(AddressDto dto) {
        return new Address(dto.getCity(), dto.getDetailed());
    }
}
