package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.PositionDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
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
    public Long update(Long userId, UserUpdateRequest request) {
        User user = userFindService.findById(userId);

        user.update(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhoneNumber(),
                request.getIntroduction(),
                toUserPosition(request.getUserPositionList()),
                toSkill(request.getSkillList()),
                toAddress(request.getAddress())
                );
        return user.getId();
    }

    private List<UserPosition> toUserPosition(List<PositionDto> dto) {

        List<UserPosition> collect = dto.stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toList());

        return collect;
    }

    private List<Skill> toSkill(List<SkillDto> dto) {
        List<Skill> collect = dto.stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toList());

        return collect;
    }

    private Address toAddress(AddressDto dto) {
        return new Address(dto.getCity(), dto.getDetailed());
    }
}
