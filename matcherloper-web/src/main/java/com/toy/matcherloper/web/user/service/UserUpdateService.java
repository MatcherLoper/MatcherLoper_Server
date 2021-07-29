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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindService userFindService;

    @Transactional
    public Long update(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userFindService.findById(userId);

        user.update(
                userUpdateRequest.getEmail(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getIntroduction(),
                ToUserPositionList(userUpdateRequest),
                ToSkillList(userUpdateRequest),
                toAddress(userUpdateRequest.getAddressDto())
                );
        return user.getId();
    }

    private List<UserPosition> ToUserPositionList(UserUpdateRequest userUpdateRequest) {

        List<UserPosition> collect = userUpdateRequest.getUserPositionDtoList().stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toList());

        return collect;
    }

    private List<Skill> ToSkillList(UserUpdateRequest userUpdateRequest) {
        List<Skill> collect = userUpdateRequest.getSkillDtoList().stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toList());

        return collect;
    }

    private Address toAddress(AddressDto dto) {
        return new Address(dto.getCity(), dto.getDetailed());
    }
}
