package com.toy.matcherloper.web.utils;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoConverter {

    public static Set<UserPosition> toUserPositionSet(List<UserPositionDto> userPositionDtoList) {
        return userPositionDtoList.stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toSet());
    }

    public static Set<Skill> toSkillSet(List<SkillDto> skillDtoList) {
        return skillDtoList.stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toSet());
    }

    public static Address toAddress(AddressDto dto) {
        return new Address(dto.getCity(), dto.getDetailed());
    }
}
