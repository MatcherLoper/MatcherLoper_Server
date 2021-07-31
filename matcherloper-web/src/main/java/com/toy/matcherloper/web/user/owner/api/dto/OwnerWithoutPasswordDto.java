package com.toy.matcherloper.web.user.owner.api.dto;

import com.toy.matcherloper.core.user.model.Owner;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OwnerWithoutPasswordDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositions;
    private List<SkillDto> skills;
    private AddressDto address;


    public OwnerWithoutPasswordDto(Owner owner) {
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.phoneNumber = owner.getPhoneNumber();
        this.introduction = owner.getIntroduction();
        this.userPositions = toUserPositionList(owner.getUserPositionSet());
        this.skills = toSkillList(owner.getSkillSet());
        this.address = new AddressDto(owner.getAddress());
    }

    private List<UserPositionDto> toUserPositionList(Set<UserPosition> userPositionSet) {
        return userPositionSet.stream()
                .map(UserPositionDto::new)
                .collect(Collectors.toList());
    }

    private List<SkillDto> toSkillList(Set<Skill> skillSet) {
        return skillSet.stream()
                .map(SkillDto::new)
                .collect(Collectors.toList());
    }
}
